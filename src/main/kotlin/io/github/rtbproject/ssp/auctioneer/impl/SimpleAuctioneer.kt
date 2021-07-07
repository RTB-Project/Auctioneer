package io.github.rtbproject.ssp.auctioneer.impl

import io.github.rtbproject.ssp.auctioneer.Auctioneer
import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionStrategy
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderOperations
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Winners
import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.Product
import io.github.rtbproject.ssp.auctioneer.impl.history.HistoryStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.*
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
        val winners: Mono<Winners> = holdAuction(lots, biddersMono)

        val holdAuction: Mono<Void> = winners.then()

        val saveHistory: Mono<Void> = saveHistory(winners, lotDescriptions)

        val notifyWinners: Mono<Void> = notifyWinners(winners)

        val sumProfit: Mono<MoneyAmount> = calculateGoodsSupplierProfit(winners)

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
            auctionResultsMono: Mono<Winners>,
            lotDescriptionsMono: Mono<LotDescriptions>
    ): Mono<Void> =
            Mono.zip(auctionResultsMono, lotDescriptionsMono)
                    .flatMap { (auctionResults, lotDescriptions) ->
                        this.historyStore.save(auctionResults, lotDescriptions)
                    }


    private fun holdAuction(lotsMono: Mono<Lots>, biddersMono: Mono<Bidders>): Mono<Winners> =
            lotsMono
                    .map { lots -> lots.values() }
                    .zipWith(biddersMono)
                    .flatMap { (lots, bidders) -> this.auctionStrategy.holdAuction(bidders, lots) }
                    .cache()


    private fun notifyWinners(winnersMono: Mono<Winners>): Mono<Void> {
        return winnersMono
                .flatMap { winners ->
                    this.bidderOperations.notifyWinners(winners)
                }
    }

    private fun calculateGoodsSupplierProfit(winnersMono: Mono<Winners>): Mono<MoneyAmount> {
        return winnersMono
                .map { winners ->
                    winners.flatMap { winner -> winner.purchasedLots }
                            .map { purchasedLot -> purchasedLot.paymentAmount }
                            .sum()
                }
    }

}