package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.impl.mock

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderCandidate
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.impl.InMemoryBidderStore
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import reactor.core.publisher.Flux
import java.net.URI
import java.util.*
import java.util.stream.Stream
import javax.inject.Singleton
import kotlin.streams.toList

@Singleton
@Requires(beans = [InMemoryBidderStore::class])
class MockDspInitializer internal constructor(private val bidderStore: InMemoryBidderStore) :
        ApplicationEventListener<ServerStartupEvent> {

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        val mockEndpoint = URI.create("http://localhost:8080")
        val saved = Stream.generate {
            BidderCandidate(UUID.randomUUID(), setOf("promocode"), mockEndpoint, mockEndpoint)
        }.limit(10).map { this.bidderStore.save(it) }.toList()
        Flux.merge(saved).collectList().subscribe()
    }
}