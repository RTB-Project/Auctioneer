package io.github.rtbproject.ssp.auctioneer.impl.history.impl

import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionResult
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.history.HistoryStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class NoOpHistoryStore : HistoryStore {

    override fun save(
            auctionResults: List<AuctionResult>,
            lotsDescriptions: LotDescriptions,
            bidders: Bidders
    ): Mono<Void> {
        return Mono.empty();
    }
}