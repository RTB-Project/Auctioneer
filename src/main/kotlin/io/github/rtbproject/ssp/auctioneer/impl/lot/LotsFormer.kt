package io.github.rtbproject.ssp.auctioneer.impl.lot

import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.Product
import reactor.core.publisher.Mono

interface LotsFormer {

    fun formLots(supplierCode: SupplierCode, productSet: Collection<Product>): Mono<Lots>
}

