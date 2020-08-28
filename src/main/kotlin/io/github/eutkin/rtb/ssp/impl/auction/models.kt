package io.github.eutkin.rtb.ssp.impl.auction

import io.github.eutkin.rtb.ssp.impl.bidder.BidderId
import io.github.eutkin.rtb.ssp.impl.lot.LotId
import io.github.eutkin.rtb.ssp.impl.lot.MoneyAmount


data class AuctionResult(val bidderId : BidderId, val lotId: LotId, val paymentAmount : MoneyAmount)

typealias AuctionResults = List<AuctionResult>



