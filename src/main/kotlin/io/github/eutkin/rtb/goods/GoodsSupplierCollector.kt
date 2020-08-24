package io.github.eutkin.rtb.goods

import io.micronaut.context.annotation.Primary
import reactor.core.publisher.Flux
import javax.inject.Singleton

@Singleton
@Primary
class GoodsSupplierCollector(private val delegates: Collection<GoodsSupplier>) : GoodsSupplier {

    override fun supply(): Flux<ProductSet> {
        return Flux
                .merge(this.delegates.map { it.supply() })
                .onErrorContinue{ex, reason -> /* fixme no-op */ }
    }
}