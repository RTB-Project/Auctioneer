package io.github.eutkin.rtb.ssp.impl.auction

import io.github.eutkin.rtb.ssp.impl.bidder.Bidders
import io.github.eutkin.rtb.ssp.impl.lot.LotId
import reactor.core.publisher.Mono

internal interface AuctionStrategy {

    fun holdAuction(bidders: Bidders, lots: Set<LotId>): Mono<List<AuctionResult>>
}