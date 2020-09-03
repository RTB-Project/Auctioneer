package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.InviteResponse
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import io.github.rtbproject.ssp.auctioneer.impl.lot.PurchasedLot
import reactor.core.publisher.Mono
import java.net.URI

internal interface BidderCommunicator {

    fun invite (bidder : BidderCandidate, lotDescriptions: LotDescriptions) : Mono<InviteResponse>

    fun winNotify(bidderWinEndpoint: URI, purchasedLots: List<PurchasedLot>): Mono<Void>
}