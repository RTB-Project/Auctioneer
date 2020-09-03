package io.github.rtbproject.ssp.auctioneer.impl.auction.impl

import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionStrategy
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidAmount
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidder
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Winners
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotValue
import io.github.rtbproject.ssp.auctioneer.impl.lot.PurchasedLot
import reactor.core.publisher.Mono
import javax.inject.Singleton

private data class FlatBidder(
        val bidder: Bidder,
        val lotId: LotId,
        val bidAmount: BidAmount
) : Comparable<FlatBidder> {

    override fun compareTo(other: FlatBidder): Int {
        return other.bidAmount.compareTo(this.bidAmount)
    }

}

@Singleton
internal class HighStakes : AuctionStrategy {

    override fun holdAuction(bidders: Bidders, lots: Map<LotId, LotValue>): Mono<Winners> {
        return Mono.fromCallable {
            bidders
                    .flattenByLot()
                    .groupBy { bidder -> bidder.lotId }
                    .values
                    .map { groupedBiddersByLot ->
                        groupedBiddersByLot.maxBy { it.bidAmount }!!
                    }
                    .groupBy { (bidder, _, _) -> bidder }
                    .map { (bidder, groupedBidders) ->
                        val purchasedLots = groupedBidders.map {
                            val lotValue = lots[it.lotId]!!
                            PurchasedLot(lotValue, it.bidAmount)
                        }
                        bidder.toWinner(purchasedLots)
                    }
        }
    }

    private fun Bidders.flattenByLot(): List<FlatBidder> {
        return this.flatMap { bidder ->
            bidder.bids.map { (lotId, bidAmount) ->
                FlatBidder(bidder, lotId, bidAmount)
            }
        }
    }
}