package io.github.rtbproject.ssp.auctioneer.impl.auction

import io.github.rtbproject.ssp.auctioneer.impl.bidder.DspId
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount

data class AuctionResult(
        val lotId: LotId,
        val bidderId: DspId,
        val paymentAmount: MoneyAmount
)