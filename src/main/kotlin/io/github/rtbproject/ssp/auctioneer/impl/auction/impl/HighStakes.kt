package io.github.rtbproject.ssp.auctioneer.impl.auction.impl

import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionResult
import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionStrategy
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidAmount
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderId
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
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

    override fun holdAuction(bidders: Bidders, lots: List<LotId>): Mono<List<AuctionResult>> {
        return Mono.fromSupplier {
            bidders
                    .flattenByLot()
                    .groupBy { flatBidder -> flatBidder.lotId }
                    .values
                    .map { biddersByLot ->
                        biddersByLot.maxBy { bidder -> bidder.bidAmount }!!
                    }
                    .map { winner -> AuctionResult(winner.lotId, winner.id, winner.bidAmount) }
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