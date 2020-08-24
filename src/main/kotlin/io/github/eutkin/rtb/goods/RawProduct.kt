package io.github.eutkin.rtb.goods

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(
        description = "Supplied product.",
        accessMode = Schema.AccessMode.WRITE_ONLY
)
data class RawProduct(
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
        val quality: BigDecimal = price
)