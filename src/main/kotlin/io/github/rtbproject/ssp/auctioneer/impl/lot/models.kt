package io.github.rtbproject.ssp.auctioneer.impl.lot

import java.math.BigDecimal
import java.util.*

typealias LotId = UUID

typealias ProductType = String

data class LotDescription(
        val id: LotId,
        val productType: ProductType,
        val supplierCode: SupplierCode,
        val minimalPrice: MoneyAmount,
        val description: Map<String, Any>
)

data class LotValue(
        val id: LotId,
        val value: Map<String, Any>
)

class Lot private constructor(
        val id: LotId,
        val value: LotValue,
        val description: LotDescription,
        private val quality: Double
) : Comparable<Lot> {


    override fun compareTo(other: Lot): Int {
        return this.quality.compareTo(other.quality)
    }

    companion object {

        fun of(
                id: LotId,
                value: Map<String, Any>,
                productType: ProductType,
                supplierCode: SupplierCode,
                minimalPrice: MoneyAmount,
                description: Map<String, Any>,
                quality: Double
        ): Lot {
            val lotDesc = LotDescription(id, productType, supplierCode, minimalPrice, description)
            val lotValue = LotValue(id, value)
            return Lot(id, lotValue, lotDesc, quality)
        }
    }

}

typealias LotDescriptions = List<LotDescription>
typealias LotValues = Map<LotId, LotValue>

typealias MoneyAmount = BigDecimal

typealias SupplierCode = String

data class PurchasedLot(val lotValue: LotValue, val paymentAmount: MoneyAmount)

data class Lots(private val lots: Collection<Lot>) : Map<LotId, Lot> by lots.toMap() {

    fun descriptions(): LotDescriptions {
        return this.lots.map { it.description }
    }

    fun values(): LotValues = this.lots.map { it.value }.map { Pair(it.id, it) }.toMap()

}

// TODO: 24.08.2020 need optimize and test
private fun Collection<Lot>.toMap(): Map<LotId, Lot> {
    val buf: MutableMap<LotId, Lot> = TreeMap()
    return this.sorted().map { Pair(it.id, it) }.toMap(buf)
}