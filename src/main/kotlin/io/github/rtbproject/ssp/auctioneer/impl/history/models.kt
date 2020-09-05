package io.github.rtbproject.ssp.auctioneer.impl.history

import io.github.rtbproject.ssp.auctioneer.impl.bidder.DspId
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotDescription
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount

data class SoldLot(
        val winner: DspId,
        val lotDescription: LotDescription,
        val paymentAmount: MoneyAmount
)
typealias SoldLots = List<SoldLot>