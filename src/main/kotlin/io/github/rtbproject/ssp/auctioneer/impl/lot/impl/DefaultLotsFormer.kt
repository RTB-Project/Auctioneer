package io.github.rtbproject.ssp.auctioneer.impl.lot.impl

import io.github.rtbproject.ssp.auctioneer.impl.entrypoint.Product
import io.github.rtbproject.ssp.auctioneer.impl.lot.Lot
import io.github.rtbproject.ssp.auctioneer.impl.lot.Lots
import io.github.rtbproject.ssp.auctioneer.impl.lot.LotsFormer
import io.github.rtbproject.ssp.auctioneer.impl.lot.SupplierCode
import reactor.core.publisher.Mono
import java.util.*
import javax.inject.Singleton

@Singleton
class DefaultLotsFormer : LotsFormer {

    override fun provideLots(supplierCode: SupplierCode, productSet: Collection<Product>): Mono<Lots> {
        return Mono
                .fromSupplier {
                    productSet.map {
                        Lot.of(
                                id = UUID.randomUUID(),
                                value = it.value,
                                productType = it.productType,
                                supplierCode = supplierCode,
                                minimalPrice = it.price,
                                description = it.description,
                                quality = it.quality
                        )
                    }
                }
                .map { Lots(it) }
    }
}