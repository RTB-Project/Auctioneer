package io.github.rtbproject.ssp.auctioneer.impl.auction

import io.github.rtbproject.ssp.auctioneer.impl.bidder.BidderId
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotId
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount


data class AuctionResult(val bidderId : BidderId, val lotId: LotId, val paymentAmount : MoneyAmount)

typealias AuctionResults = List<AuctionResult>



