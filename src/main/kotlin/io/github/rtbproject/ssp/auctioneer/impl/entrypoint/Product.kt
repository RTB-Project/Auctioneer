package io.github.rtbproject.ssp.auctioneer.impl.entrypoint

import io.github.rtbproject.ssp.auctioneer.impl.lot.ProductType
import java.math.BigDecimal
import java.util.*

typealias ProductId = UUID

data class Product(

        val id: ProductId,

        val description: Map<String, Any>,

        val value: Map<String, Any>,

        val type: ProductType,

        val price: BigDecimal = BigDecimal.ZERO,

        val quality: Double = price.toDouble()
)