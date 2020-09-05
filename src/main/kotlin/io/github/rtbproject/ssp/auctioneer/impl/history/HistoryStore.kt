package io.github.rtbproject.ssp.auctioneer.impl.history

import reactor.core.publisher.Mono


interface HistoryStore {

    fun save(soldLots: SoldLots): Mono<Void>
}