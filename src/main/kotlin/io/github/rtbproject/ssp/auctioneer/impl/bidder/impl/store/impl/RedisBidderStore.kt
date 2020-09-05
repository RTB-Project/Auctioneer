package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderId
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.BidderStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import io.lettuce.core.KeyValue
import io.lettuce.core.api.StatefulRedisConnection
import reactor.core.publisher.Flux

class RedisBidderStore(
        private val redisClient: StatefulRedisConnection<ProductType, BidderCandidate>
) : BidderStore {

    override fun findByLotDescriptions(lotDescriptions: LotDescriptions): Flux<BidderCandidate> {
        val productTypes = lotDescriptions
                .map { lotDescription -> lotDescription.productType }
                .toTypedArray()
        return this.redisClient
                .reactive()
                .mget(*productTypes)
                .map { (_, value) -> value }
    }

}

operator fun <K, V> KeyValue<K, V>.component1(): K {
    return this.key
}

operator fun <K, V> KeyValue<K, V>.component2(): V {
    return this.value
}