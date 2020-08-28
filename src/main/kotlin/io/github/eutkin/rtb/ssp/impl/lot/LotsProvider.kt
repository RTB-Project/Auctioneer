package io.github.eutkin.rtb.ssp.impl.lot

import io.github.eutkin.rtb.ssp.impl.entrypoint.Product
import reactor.core.publisher.Mono

internal interface LotsProvider {

    fun provideLots(supplierCode: SupplierCode, productSet : Collection<Product>): Mono<Lots>
}

