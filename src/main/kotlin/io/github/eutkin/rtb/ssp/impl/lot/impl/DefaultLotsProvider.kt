package io.github.eutkin.rtb.ssp.impl.lot.impl

import io.github.eutkin.rtb.ssp.impl.entrypoint.Product
import io.github.eutkin.rtb.ssp.impl.lot.*
import reactor.core.publisher.Mono
import java.util.*
import javax.inject.Singleton

@Singleton
class DefaultLotsProvider : LotsProvider {

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