package io.github.eutkin.rtb.goods.mock

import io.github.eutkin.rtb.goods.GoodsSupplier
import io.github.eutkin.rtb.goods.Product
import io.github.eutkin.rtb.goods.ProductSet
import io.github.eutkin.rtb.goods.RawProduct
import io.micronaut.http.HttpStatus.ACCEPTED
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Tag(name = "Goods")
@Controller("/api/goods/mock")
class MockGoodsSupplierEndpoint : GoodsSupplier {

    private val processor: DirectProcessor<ProductSet> = DirectProcessor.create()

    private val supplierCode = "mock"

    @Operation(summary = "Consume product set")
    @Post
    @Status(ACCEPTED)
    fun consume(
            @Body productSet: Flux<RawProduct>,
            @Parameter(
                    description = "Type of product. Should be a previously agreed constant",
                    required = true
            )
            @Header productType: String
    ): Mono<Void> {
        return productSet
                .map {
                    Product(
                            it.description,
                            it.value,
                            it.price,
                            it.quality,
                            productType,
                            supplierCode
                    )
                }
                .collectList()
                .map { ProductSet(it) }
                .doOnNext { this.processor.onNext(it) }
                .then()
    }

    override fun supply(): Flux<ProductSet> {
        return this.processor.onBackpressureBuffer()
    }
}