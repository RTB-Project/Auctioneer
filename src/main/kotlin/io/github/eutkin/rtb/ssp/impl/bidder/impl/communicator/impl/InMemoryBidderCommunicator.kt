package io.github.eutkin.rtb.ssp.impl.bidder.impl.communicator.impl

import io.github.eutkin.rtb.ssp.impl.bidder.*
import io.github.eutkin.rtb.ssp.impl.bidder.impl.communicator.BidderCommunicator
import io.github.eutkin.rtb.ssp.impl.lot.LotDescriptions
import io.github.eutkin.rtb.ssp.impl.lot.LotId
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.MathContext
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Singleton

@Singleton
internal class InMemoryBidderCommunicator : BidderCommunicator {

    override fun invite(bidder: BidderCandidate, lotDescriptions: LotDescriptions): Mono<InviteResponse> {
        return Mono.fromCallable {
            val random = ThreadLocalRandom.current()
            val accept: Boolean = random.nextBoolean()
            if (accept) {
               val rates: Map<LotId, BidAmount> = lotDescriptions.map {
                    val price = it.minimalPrice.toDouble()
                   val rate = BigDecimal(random.nextDouble(price, price + 100), MathContext(2))
                   Pair(it.id, rate)
                }.toMap()
                Bid(bidderId = bidder.id, rates = rates)
            } else {
                Decline(bidderId = bidder.id)
            }
        }
    }

    override fun winNotify(bidder: BidderCandidate, winNotifications: WinNotifications): Mono<Void> {
        return Mono.empty()
    }
}