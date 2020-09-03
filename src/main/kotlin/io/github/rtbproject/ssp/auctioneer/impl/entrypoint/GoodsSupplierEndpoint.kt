package io.github.rtbproject.ssp.auctioneer.impl.entrypoint

import io.github.rtbproject.ssp.auctioneer.Auctioneer
import io.github.rtbproject.ssp.auctioneer.impl.lot.MoneyAmount
import io.github.rtbproject.ssp.auctioneer.impl.lot.SupplierCode
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono

@Tag(name = "Goods")
@Controller("/api/goods")
 class GoodsSupplierEndpoint(
        private val auctioneer: Auctioneer
) {

    @Operation(summary = "Consume product set")
    @Post("/{supplierCode}")
    @Status(HttpStatus.OK)
    fun consume(
            @Body products: Mono<Collection<Product>>,
            @PathVariable supplierCode: SupplierCode
    ): Mono<MoneyAmount> {
        return products
                .flatMap { productSet -> this.auctioneer.sellGoods(supplierCode, productSet) }
    }
}