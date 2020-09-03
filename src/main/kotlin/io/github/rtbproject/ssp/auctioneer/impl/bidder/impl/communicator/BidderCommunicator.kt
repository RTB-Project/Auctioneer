package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.InviteResponse
import io.github.rtbproject.ssp.auctioneer.impl.bidder.WinNotifications
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Mono

internal interface BidderCommunicator {

    fun invite (bidder : BidderCandidate, lotDescriptions: LotDescriptions) : Mono<InviteResponse>

    fun winNotify(bidder: BidderCandidate, winNotifications: WinNotifications) : Mono<Void>
}