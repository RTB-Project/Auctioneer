package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.Bid
import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidAmount
import io.github.rtbproject.ssp.auctioneer.impl.bidder.Decline
import io.github.rtbproject.ssp.auctioneer.impl.bidder.InviteResponse
import io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator.BidderCommunicator
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescriptions
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.PurchasedLot
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.MathContext
import java.net.URI
import java.time.Duration
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Singleton

@Singleton
internal class InMemoryBidderCommunicator : BidderCommunicator {

    override fun invite(inviteEndpoint: URI, lotDescriptions: LotDescriptions): Mono<InviteResponse> {
        return Mono.fromSupplier {
            val random = ThreadLocalRandom.current()
            val accept: Boolean = random.nextBoolean()
            if (accept) {
                val bids: Map<LotId, BidAmount> = lotDescriptions.map {
                    val price = it.minimalPrice.toDouble()
                    val bid = BigDecimal(random.nextDouble(price, price + 100), MathContext(2))
                    Pair(it.id, bid)
                }.toMap()
                Bid(bids)
            } else {
                Decline
            }
        }
    }

    override fun winNotify(winEndpoint: URI, purchasedLots: List<PurchasedLot>): Mono<Void> {
        return Mono.delay(Duration.ofMillis(10)).then()
    }
}