package io.github.eutkin.rtb.ssp.impl.bidder.impl

import io.github.eutkin.rtb.ssp.impl.bidder.*
import io.github.eutkin.rtb.ssp.impl.bidder.impl.communicator.BidderCommunicator
import io.github.eutkin.rtb.ssp.impl.bidder.impl.store.BidderStore
import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
import io.github.eutkin.rtb.ssp.impl.lot.PurchasedLot
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
                .flatMap { bidderCandidate -> this.bidderCommunicator.invite(bidderCandidate, lotDescriptions) }
                .filter { it is Bid }
                .map { it as Bid }
                .map { Bidder(it.bidderId, it.rates) }
                .collectList()
    }

    override fun notifyWinners(winners: Map<BidderId, List<PurchasedLot>>): Mono<Void> {
        return this.bidderStore
                .findByIds(winners.keys)
                .flatMap { dsp ->
                    val winNotifications = winners.getValue(dsp.id)
                            .map { WinNotification(it.lotValue, it.paymentAmount) }
                            .toList()
                    this.bidderCommunicator.winNotify(dsp, winNotifications)
                }
                .then()
    }

}