package io.github.rtbproject.ssp.auctioneer.impl.history.impl

import io.github.rtbproject.ssp.auctioneer.impl.history.HistoryStore
import io.github.rtbproject.ssp.auctioneer.impl.history.SoldLots
import reactor.core.publisher.Mono
import java.time.Duration
import javax.inject.Singleton

@Singleton
class NoOpHistoryStore : HistoryStore {


    override fun save(soldLots: SoldLots): Mono<Void> {
        return Mono.delay(Duration.ofMillis(10)).then()
    }
}