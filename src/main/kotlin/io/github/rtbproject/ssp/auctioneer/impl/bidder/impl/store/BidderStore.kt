package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store

import io.github.rtbproject.ssp.auctioneer.impl.bidder.DSP
import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import reactor.core.publisher.Flux

interface BidderStore {

    fun findByProductTypes(productTypes: Collection<ProductType>): Flux<DSP>

}