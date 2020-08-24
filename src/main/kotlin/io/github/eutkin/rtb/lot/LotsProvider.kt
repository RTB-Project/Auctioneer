package io.github.eutkin.rtb.lot

import io.github.eutkin.rtb.goods.ProductSet
import reactor.core.publisher.Mono

interface LotsProvider {

    fun provide(productSet: ProductSet) : Mono<Lots>
}