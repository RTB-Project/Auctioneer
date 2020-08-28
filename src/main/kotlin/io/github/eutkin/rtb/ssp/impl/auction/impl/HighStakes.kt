package io.github.eutkin.rtb.ssp.impl.auction.impl

import io.github.eutkin.rtb.ssp.impl.auction.AuctionResult
import io.github.eutkin.rtb.ssp.impl.auction.AuctionStrategy
import io.github.eutkin.rtb.ssp.impl.bidder.BidAmount
import io.github.eutkin.rtb.ssp.impl.bidder.BidderId
import io.github.eutkin.rtb.ssp.impl.bidder.Bidders
import io.github.eutkin.rtb.ssp.impl.lot.LotId
import reactor.core.publisher.Mono
import javax.inject.Singleton

private data class FlatBidder(
        val id: BidderId,
        val lotId: LotId,
        val bidAmount: BidAmount
) : Comparable<FlatBidder> {

    override fun compareTo(other: FlatBidder): Int {
        return other.bidAmount.compareTo(this.bidAmount)
    }

}

@Singleton
internal class HighStakes : AuctionStrategy {

    override fun holdAuction(bidders: Bidders, lots: Set<LotId>): Mono<List<AuctionResult>> {
        return Mono.fromCallable {
            bidders
                    .flattenByLot()
                    .groupBy { bidder -> bidder.lotId }
                    .values
                    .map { bidder -> bidder.maxBy { it.bidAmount }!! }
                    .map { bidder -> AuctionResult(bidder.id, bidder.lotId, bidder.bidAmount) }
        }
    }

    private fun Bidders.flattenByLot(): List<FlatBidder> {
        return this.flatMap { bidder ->
            bidder.bids.map { (lotId, bidAmount) ->
                FlatBidder(bidder.id, lotId, bidAmount)
            }
        }
    }
}