package io.github.rtbproject.ssp.auctioneer.impl.history

import io.github.rtbproject.ssp.auctioneer.impl.bidder.Winners
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Mono

interface HistoryStore {

    fun save(winners: Winners, lotsDescriptions: LotDescriptions): Mono<Void>
}