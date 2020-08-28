package io.github.eutkin.rtb.ssp.impl.entrypoint

import io.github.eutkin.rtb.ssp.impl.lot.ProductType
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(
        description = "Supplied product.",
        accessMode = Schema.AccessMode.WRITE_ONLY
)
data class Product(
        @Schema(
                description = "Product description. Translate to all bidder.",
                required = true,
                example = """
                    {
                        "game": "FIFA07"
                    }
                """
        )
        val description: Map<String, Any>,

        @Schema(
                description = "Secret product part. Translate to auction winner.",
                required = true,
                example = """
                    {
                        "code": "SUMMER2007"
                    }
                """
        )
        val value: Map<String, Any>,

        @Schema(
                description = "Type of product. Should be a previously agreed constant",
                example = "promocode",
                required = true
        )
        val productType: ProductType,

        @Schema(
                description = "Minimal price.",
                defaultValue = "0",
                example = "109.9"
        )
        val price: BigDecimal = BigDecimal.ZERO,

        @Schema(
                description = """
                   Degree of product quality relative to other products in the set. 
                   Default value is minimal price 
                """",
                example = "10",
                defaultValue = "109.9"
        )
        val quality: Double = price.toDouble()
)