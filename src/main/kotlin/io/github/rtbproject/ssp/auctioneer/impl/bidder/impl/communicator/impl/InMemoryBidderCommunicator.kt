package io.github.rtbproject.ssp.auctioneer.impl.bidder.impl.communicator.impl

import io.github.rtbproject.ssp.auctioneer.impl.bidder.*
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

    override fun invite(bidder: BidderCandidate, lotDescriptions: LotDescriptions): Mono<InviteResponse> {
        return Mono.fromCallable {
            val random = ThreadLocalRandom.current()
            val accept: Boolean = random.nextBoolean()
            if (accept) {
                val bids: Map<LotId, BidAmount> = lotDescriptions.map {
                    val price = it.minimalPrice.toDouble()
                    val bid = BigDecimal(random.nextDouble(price, price + 100), MathContext(2))
                    Pair(it.id, bid)
                }.toMap()
                Bid(bidderId = bidder.id, bids = bids)
            } else {
                Decline(bidderId = bidder.id)
            }
        }
    }

    override fun winNotify(bidderWinEndpoint: URI, purchasedLots: List<PurchasedLot>): Mono<Void> {
        return Mono.delay(Duration.ofMillis(10)).then()
    }
}