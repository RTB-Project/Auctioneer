package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderId
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal interface BidderStore {

    fun save(bidder : BidderCandidate) : Mono<BidderCandidate>

    fun findByLotDescriptions(lotDescriptions: LotDescriptions) : Flux<BidderCandidate>

    fun findByIds(ids: Collection<BidderId>): Flux<BidderCandidate>
}