package io.github.rtbproject.ssp.auctioneer.impl.auction

import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidder
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import reactor.core.publisher.Mono

interface AuctionStrategy {

    fun holdAuction(bidders: List<Bidder>, lots: List<LotId>): Mono<List<AuctionResult>>
}