package io.github.rtbproject.ssp.auctioneer.impl.entrypoint

import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import java.math.BigDecimal

data class Product(

        val description: Map<String, Any>,

        val value: Map<String, Any>,

        val productType: ProductType,

        val price: BigDecimal = BigDecimal.ZERO,

        val quality: Double = price.toDouble()
)