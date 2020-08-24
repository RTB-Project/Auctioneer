package io.github.eutkin.rtb.goods

import reactor.core.publisher.Flux

interface GoodsSupplier {

    fun supply() : Flux<ProductSet>
}