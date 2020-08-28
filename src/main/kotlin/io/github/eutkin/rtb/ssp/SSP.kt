package io.github.eutkin.rtb.ssp

import io.github.eutkin.rtb.ssp.impl.entrypoint.Product
import io.github.eutkin.rtb.ssp.impl.lot.MoneyAmount
import io.github.eutkin.rtb.ssp.impl.lot.SupplierCode
import reactor.core.publisher.Mono

interface SSP {

    fun sellGoods(supplierCode: SupplierCode, productSet: Collection<Product>): Mono<MoneyAmount>
}