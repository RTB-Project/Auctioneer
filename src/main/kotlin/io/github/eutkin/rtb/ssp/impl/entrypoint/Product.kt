package io.github.eutkin.rtb.ssp.impl.entrypoint

import io.github.eutkin.rtb.ssp.impl.lot.ProductType
import java.math.BigDecimal

data class Product(

        val description: Map<String, Any>,

        val value: Map<String, Any>,

        val productType: ProductType,

        val price: BigDecimal = BigDecimal.ZERO,

        val quality: Double = price.toDouble()
)