package io.github.rtbproject.ssp.auctioneer.impl.bidder

import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotValue
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount
import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
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

data class Bidder(val id: BidderId, val bids: Map<LotId, BidAmount>)

typealias Bidders = List<Bidder>

sealed class InviteResponse(val bidderId: BidderId)

class Bid(bidderId: BidderId, val rates: Map<LotId, BigDecimal>) : InviteResponse(bidderId)

class Decline(bidderId: BidderId) : InviteResponse(bidderId)

data class WinNotification(val lot : LotValue, val paymentAmount : MoneyAmount)

typealias WinNotifications = Collection<WinNotification>



