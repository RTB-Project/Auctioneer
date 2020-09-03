package io.github.rtbproject.ssp.auctioneer.impl.history

import io.github.rtbproject.ssp.auctioneer.impl.auction.AuctionResult
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bidders
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Mono

interface HistoryStore {

    fun save(
            auctionResults: List<AuctionResult>,
            lotsDescriptions: LotDescriptions,
            bidders: Bidders
    ) : Mono<Void>
}