package io.github.rtbproject.ssp.auctioneer.impl.auction

import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import reactor.core.publisher.Mono

internal interface AuctionStrategy {

    fun holdAuction(bidders: Bidders, lots: Set<LotId>): Mono<List<AuctionResult>>
}