package io.github.rtbproject.ssp.auctioneer.impl.bidder

import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Mono

interface BidderOperations {

    fun invite(lotDescriptions: LotDescriptions): Mono<Bidders>

    fun notifyWinners(winners: List<Winner>): Mono<Void>
}