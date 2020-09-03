package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderId
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.BidderStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import io.micronaut.context.annotation.Requires
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Singleton
import kotlin.concurrent.withLock

@Singleton
@Requires(missingBeans = [BidderStore::class])
internal class InMemoryBidderStore : BidderStore {

    private val store: MutableMap<BidderId, BidderCandidate> = HashMap()

    private val productTypesIndex: MutableMap<ProductType, MutableList<BidderId>> = HashMap()

    private val lock: ReadWriteLock = ReentrantReadWriteLock()

    fun save(bidder: BidderCandidate): Mono<BidderCandidate> {
        return Mono.fromCallable {
            val writeLock = lock.writeLock()
            writeLock.withLock {
                this.store[bidder.id] = bidder
                bidder.productTypes.forEach { this.productTypesIndex.getOrPut(it) { mutableListOf() }.add(bidder.id) }
                bidder
            }

        }
    }

    override fun findByLotDescriptions(lotDescriptions: LotDescriptions): Flux<BidderCandidate> {
        return Flux.defer {
            this.lock.readLock().withLock {
                Flux.fromIterable(
                        lotDescriptions
                                .map { it.productType }
                                .filter { this.productTypesIndex.containsKey(it) }
                                .flatMap { this.productTypesIndex[it]!! }
                                .map { this.store[it]!! })
            }
        }
    }


    override fun findByIds(ids: Collection<BidderId>): Flux<BidderCandidate> {
        return Flux.defer {
            this.lock.readLock().withLock {
                Flux.fromIterable(ids.filter { this.store.containsKey(it) }.map { this.store[it]!! })
            }
        }
    }
}