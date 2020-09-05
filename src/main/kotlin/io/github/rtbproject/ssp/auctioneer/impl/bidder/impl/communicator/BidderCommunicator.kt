package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator

import io.github.rtbproject.ssp.auctioneer.impl.bidder.InviteResponse
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import io.github.rtbproject.ssp.auctioneer.impl.lot.PurchasedLot
import reactor.core.publisher.Mono
import java.net.URI

interface BidderCommunicator {

    fun invite(inviteEndpoint: URI, lotDescriptions: LotDescriptions): Mono<InviteResponse>

    fun winNotify(winEndpoint: URI, purchasedLots: List<PurchasedLot>): Mono<Void>
}