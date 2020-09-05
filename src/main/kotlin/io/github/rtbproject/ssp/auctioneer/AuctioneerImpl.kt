package io.github.rtbproject.ssp.auctioneer

import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionResult
import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionStrategy
import io.github.rtbproject.ssp.auctioneer.impl.bidder.*
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator.BidderCommunicator
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.BidderStore
import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.Product
import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.ProductId
import io.github.rtbproject.ssp.auctioneer.impl.history.HistoryStore
import io.github.rtbproject.ssp.auctioneer.impl.history.SoldLot
import io.github.rtbproject.ssp.auctioneer.impl.lot.*
import reactor.core.publisher.Mono

class AuctioneerImpl(
        private val bidderStore: BidderStore,
        private val lotsFormer: LotsFormer,
        private val bidderCommunicator: BidderCommunicator,
        private val auctionStrategy: AuctionStrategy,
        private val historyStore: HistoryStore
) : Auctioneer {

    override fun sellGoods(
            supplierCode: SupplierCode,
            productSet: Collection<Product>
    ): Mono<Map<ProductId, MoneyAmount>> {

        val lotsMono = this.lotsFormer.formLots(supplierCode, productSet).cache()

        val productTypes = productSet.map { product -> product.type }

        val dspFlux = this.bidderStore.findByProductTypes(productTypes).cache()

        val auctionResults: Mono<List<AuctionResult>> = dspFlux
                .map { BidderCandidate(it.id, it.inviteEndpoint) }
                .concatMap { bidderCandidate ->
                    lotsMono
                            .map { lots -> lots.descriptions() }
                            .flatMap { lots ->
                                this.bidderCommunicator.invite(bidderCandidate.inviteEndpoint, lots)
                                        .filter { response -> response is Bid }
                                        .map { response -> response as Bid }
                                        .map { (bids) -> Bidder(bidderCandidate.id, bids) }
                            }
                }
                .collectList()
                .flatMap { bidders ->
                    lotsMono
                            .map { lots -> lots.map { lot -> lot.id } }
                            .flatMap { lots -> this.auctionStrategy.holdAuction(bidders, lots) }
                }

        auctionResults
                .flatMap { results ->
                    lotsMono
                            .map { lots -> lots.descriptions().map { desc -> Pair(desc.id, desc) }.toMap() }
                            .map { lots ->
                                results.map { (lotId, dspId, paymentAmount) -> SoldLot(dspId, lots[lotId]!!, paymentAmount) }
                            }
                }
                .flatMap { soldLots -> this.historyStore.save(soldLots) }
                .map { }


    }


}

