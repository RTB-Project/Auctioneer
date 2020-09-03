package io.github.rtbproject.ssp.auctioneer

import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.Product
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount
import io.github.rtbproject.ssp.auctioneer.impl.lot.SupplierCode
import reactor.core.publisher.Mono

interface Auctioneer {

    fun sellGoods(supplierCode: SupplierCode, productSet: Collection<Product>): Mono<MoneyAmount>
}