package io.github.rtbproject.ssp.auctioneer.impl.history.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.Winners
import io.github.rtbproject.ssp.auctioneer.impl.history.HistoryStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class NoOpHistoryStore : HistoryStore {

    override fun save(winners: Winners, lotsDescriptions: LotDescriptions): Mono<Void> {
        return Mono.empty();
    }
}