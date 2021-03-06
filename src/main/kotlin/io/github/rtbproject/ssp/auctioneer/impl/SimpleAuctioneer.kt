package io.github.rtbproject.ssp.auctioneer.impl

import io.github.rtbproject.ssp.auctioneer.Auctioneer
import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionResult
import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionResults
import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionStrategy
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderId
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderOperations
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.Product
import io.github.rtbproject.ssp.auctioneer.impl.history.HistoryStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class SimpleAuctioneer internal constructor(
        private val lotsFormer: LotsFormer,
        private val bidderOperations: BidderOperations,
        private val auctionStrategy: AuctionStrategy,
        private val historyStore: HistoryStore
) : Auctioneer {

    override fun sellGoods(supplierCode: SupplierCode, productSet: Collection<Product>): Mono<MoneyAmount> {
        val lots: Mono<Lots> = formLots(supplierCode, productSet)
        val lotDescriptions: Mono<LotDescriptions> = describeLots(lots)
        val biddersMono: Mono<Bidders> = inviteBidders(lotDescriptions)
        val auctionResults: Mono<AuctionResults> = holdAuction(lots, biddersMono)

        val holdAuction: Mono<Void> = auctionResults.then()

        val saveHistory: Mono<Void> = saveHistory(auctionResults, lotDescriptions, biddersMono)

        val notifyWinners: Mono<Void> = notifyWinners(auctionResults, lots.values())

        val sumProfit: Mono<MoneyAmount> = calculateGoodsSupplierProfit(auctionResults)

        return holdAuction.then(saveHistory).then(notifyWinners).then(sumProfit)

    }

    private fun formLots(supplierCode: SupplierCode, productSet: Collection<Product>): Mono<Lots> =
            this.lotsFormer
                    .provideLots(supplierCode, productSet).cache()

    private fun describeLots(lotsMono: Mono<Lots>): Mono<LotDescriptions> =
            lotsMono
                    .map { lots -> lots.descriptions() }.cache()

    private fun inviteBidders(lotDescriptionsMono: Mono<LotDescriptions>): Mono<Bidders> =
            lotDescriptionsMono
                    .flatMap { lotDescriptions -> this.bidderOperations.invite(lotDescriptions) }

    private fun saveHistory(
            auctionResultsMono: Mono<AuctionResults>,
            lotDescriptionsMono: Mono<LotDescriptions>,
            biddersMono: Mono<Bidders>
    ): Mono<Void> =
            Flux.zip(auctionResultsMono, lotDescriptionsMono, biddersMono)
                    .next()
                    .flatMap { (auctionResults, lotDescriptions, bidders) ->
                        this.historyStore.save(auctionResults, lotDescriptions, bidders)
                    }


    private fun holdAuction(lotsMono: Mono<Lots>, biddersMono: Mono<Bidders>): Mono<AuctionResults> =
            lotsMono
                    .map { lots -> lots.identifiers }
                    .zipWith(biddersMono)
                    .flatMap { (lots, bidders) -> this.auctionStrategy.holdAuction(bidders, lots) }
                    .cache()


    private fun groupLotByBidder(auctionResults: List<AuctionResult>, lots: Map<LotId, LotValue>)
            : Map<BidderId, List<PurchasedLot>> {
        return auctionResults
                .map { auctionResult -> Pair(auctionResult, lots[auctionResult.lotId]!!) }
                .groupBy(
                        { (auctionResult, _) -> auctionResult.bidderId },
                        { (auctionResult, lot) ->
                            PurchasedLot(lot, auctionResult.paymentAmount)
                        }
                )
    }

    private fun notifyWinners(auctionResultsMono: Mono<List<AuctionResult>>, lotsMono: Mono<Map<LotId, LotValue>>): Mono<Void> {
        return auctionResultsMono
                .zipWith(lotsMono)
                .map { (results, lots) ->
                    this.groupLotByBidder(results, lots)
                }
                .flatMap { winners ->
                    this.bidderOperations.notifyWinners(winners)
                }
    }

    private fun calculateGoodsSupplierProfit(auctionResultsMono: Mono<List<AuctionResult>>): Mono<MoneyAmount> {
        return auctionResultsMono
                .map { results ->
                    results.map { it.paymentAmount }.sum()
                }
    }

    private fun Mono<Lots>.values(): Mono<Map<LotId, LotValue>> = this.map { lots ->
        lots.mapValues { (_, lot) -> lot.value }
    }

}