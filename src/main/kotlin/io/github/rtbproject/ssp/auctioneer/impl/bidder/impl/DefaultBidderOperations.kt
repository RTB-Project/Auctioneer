package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.*
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator.BidderCommunicator
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.BidderStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
internal class DefaultBidderOperations(
        private val bidderStore: BidderStore,
        private val bidderCommunicator: BidderCommunicator
) : BidderOperations {

    override fun invite(lotDescriptions: LotDescriptions): Mono<Bidders> {
        return this.bidderStore
                .findByLotDescriptions(lotDescriptions)
                .flatMap { bidderCandidate ->
                    this.bidderCommunicator
                            .invite(bidderCandidate, lotDescriptions)
                            .filter { it is Bid }
                            .map { it as Bid }
                            .map { (bidderId, bids) ->
                                Bidder(bidderId, bids, bidderCandidate.winEndpoint)
                            }
                }
                .collectList()
    }

    override fun notifyWinners(winners: Winners): Mono<Void> {
        return Flux.fromIterable(winners)
                .flatMap { (_, purchasedLots, winEndpoint) ->
                    this.bidderCommunicator.winNotify(winEndpoint, purchasedLots)
                }
                .then()
    }

}