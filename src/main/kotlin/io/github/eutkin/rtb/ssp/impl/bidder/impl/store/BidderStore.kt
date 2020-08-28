package io.github.eutkin.rtb.ssp.impl.bidder.impl.store

import io.github.eutkin.rtb.ssp.impl.bidder.BidderCandidate
import io.github.eutkin.rtb.ssp.impl.bidder.BidderId
import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal interface BidderStore {

    fun save(bidder : BidderCandidate) : Mono<BidderCandidate>

    fun findByLotDescriptions(lotDescriptions: LotDescriptions) : Flux<BidderCandidate>

    fun findByIds(ids: Collection<BidderId>): Flux<BidderCandidate>
}