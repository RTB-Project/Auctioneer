package io.github.rtbproject.ssp.auctioneer.impl.bidder

import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount
import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import io.github.rtbproject.ssp.auctioneer.impl.lot.PurchasedLot
import java.math.BigDecimal
import java.net.URI
import java.util.*


typealias BidderId = UUID

data class BidderCandidate(
        val id: BidderId,
        val productTypes: Collection<ProductType>,
        val inviteEndpoint: URI,
        val winEndpoint: URI
)

typealias BidAmount = BigDecimal

data class Bidder(
        val id: BidderId,
        val bids: Map<LotId, BidAmount>,
        private val winEndpoint: URI
) {
    fun toWinner(purchasedLots: List<PurchasedLot>): Winner =
            Winner(this.id, purchasedLots, this.winEndpoint)
}

data class Winner(val id: BidderId, val purchasedLots: List<PurchasedLot>, val windEndpoint: URI)

typealias Bidders = List<Bidder>
typealias Winners = List<Winner>

sealed class InviteResponse(val bidderId: BidderId) {
    operator fun component1(): BidderId = bidderId
}

class Bid(bidderId: BidderId, val bids: Map<LotId, MoneyAmount>) : InviteResponse(bidderId) {

    operator fun component2(): Map<LotId, MoneyAmount> = bids
}

class Decline(bidderId: BidderId) : InviteResponse(bidderId) {
}



