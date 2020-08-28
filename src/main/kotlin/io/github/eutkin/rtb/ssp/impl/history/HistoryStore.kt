package io.github.eutkin.rtb.ssp.impl.history

import io.github.eutkin.rtb.ssp.impl.auction.AuctionResult
import io.github.eutkin.rtb.ssp.impl.bidder.Bidders
import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
import reactor.core.publisher.Mono

interface HistoryStore {

    fun save(
            auctionResults: List<AuctionResult>,
                lotsDescriptions: LotDescriptions,
                bidders: Bidders
    ) : Mono<Void>
}