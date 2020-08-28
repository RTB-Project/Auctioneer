package io.github.eutkin.rtb.ssp.impl.bidder.impl.communicator

import io.github.eutkin.rtb.ssp.impl.bidder.BidderCandidate
import io.github.eutkin.rtb.ssp.impl.bidder.InviteResponse
import io.github.eutkin.rtb.ssp.impl.bidder.WinNotifications
import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
import reactor.core.publisher.Mono

internal interface BidderCommunicator {

    fun invite (bidder : BidderCandidate, lotDescriptions: LotDescriptions) : Mono<InviteResponse>

    fun winNotify(bidder: BidderCandidate, winNotifications: WinNotifications) : Mono<Void>
}