package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.*
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator.BidderCommunicator
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.store.BidderStore
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

@Singleton
internal class DefaultBidderOperations(
        private val bidderStore: BidderStore,
        private val bidderCommunicator: BidderCommunicator
) : BidderOperations {

    override fun invite(lotDescriptions: LotDescriptions): Mono<Collection<Bidder>> {
        val productTypes = lotDescriptions.map { lotDescription -> lotDescription.productType }
        this.bidderStore.findByProductTypes(productTypes)


    }

    override fun notifyWinners(winners: List<Winner>): Mono<Void> {

    }

}