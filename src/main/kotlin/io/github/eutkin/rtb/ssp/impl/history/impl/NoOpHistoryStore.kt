package io.github.eutkin.rtb.ssp.impl.history.impl

import io.github.eutkin.rtb.ssp.impl.auction.AuctionResult
import io.github.eutkin.rtb.ssp.impl.bidder.Bidders
import io.github.eutkin.rtb.ssp.impl.history.HistoryStore
import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
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