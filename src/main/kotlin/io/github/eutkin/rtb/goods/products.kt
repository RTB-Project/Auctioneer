package io.github.eutkin.rtb.goods

import java.math.BigDecimal

typealias ProductCode = String

typealias SupplierCode = String

typealias Price = BigDecimal

data class Product(
        val description: Map<String, Any>,
        val value: Map<String, Any>,
        val price: Price,
        private val quality: BigDecimal,
        val productType: ProductCode,
        val supplierCode: SupplierCode
) : Comparable<Product> {

    override fun compareTo(other: Product): Int {
        return other.quality.compareTo(this.quality)
    }
}

data class ProductSet(private val products: Collection<Product>) : Collection<Product> by products