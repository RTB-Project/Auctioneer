package io.github.rtbproject.ssp.auctioneer.impl.bidder

import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount
import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import io.github.rtbproject.ssp.auctioneer.impl.lot.PurchasedLot
import java.math.BigDecimal
import java.net.URI
import java.util.*

typealias DspId = UUID

internal data class DSP(
        val id: DspId,
        val productTypes: Collection<ProductType>,
        val inviteEndpoint: URI,
        val winEndpoint: URI
)

internal data class BidderCandidate(
        val id: DspId,
        val inviteEndpoint: URI
)

typealias BidAmount = BigDecimal

data class Bidder(val id: DspId, val bids: Map<LotId, BidAmount>)

data class Winner(val id: DspId, val purchasedLots: List<PurchasedLot>, val winEndpoint: URI)


internal sealed class InviteResponse

internal class Bid(val bids: Map<LotId, MoneyAmount>) : InviteResponse() {

    operator fun component1(): Map<LotId, MoneyAmount> = bids
}

internal object Decline : InviteResponse()



