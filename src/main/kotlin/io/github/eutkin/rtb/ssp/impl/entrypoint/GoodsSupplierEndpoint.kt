package io.github.eutkin.rtb.ssp.impl.entrypoint

import io.github.eutkin.rtb.ssp.SSP
import io.github.eutkin.rtb.ssp.impl.lot.MoneyAmount
import io.github.eutkin.rtb.ssp.impl.lot.SupplierCode
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono

@Tag(name = "Goods")
@Controller("/api/goods")
 class GoodsSupplierEndpoint(
        private val ssp: SSP
) {

    @Operation(summary = "Consume product set")
    @Post("/{supplierCode}")
    @Status(HttpStatus.OK)
    fun consume(
            @Body products: Mono<Collection<Product>>,
            @PathVariable supplierCode: SupplierCode
    ): Mono<MoneyAmount> {
        return products
                .flatMap { productSet -> this.ssp.sellGoods(supplierCode, productSet) }
    }
}