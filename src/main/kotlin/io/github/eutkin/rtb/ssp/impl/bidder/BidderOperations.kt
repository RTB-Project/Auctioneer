package io.github.eutkin.rtb.ssp.impl.bidder

import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
import io.github.eutkin.rtb.ssp.impl.lot.PurchasedLot
import reactor.core.publisher.Mono

interface BidderOperations {

    fun invite(lotDescriptions: LotDescriptions): Mono<Bidders>

    fun notifyWinners(winners: Map<BidderId, List<PurchasedLot>>) : Mono<Void>
}