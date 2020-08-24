package io.github.eutkin.rtb.lot

import io.github.eutkin.rtb.goods.Product
import java.util.*

typealias LotId = UUID

data class Lot(val id : LotId, val product : Product) : Comparable<Lot> {
    override fun compareTo(other: Lot): Int {
        return this.product.compareTo(other.product)
    }
}

data class Lots(private val lots : Collection<Lot>) : List<Lot> by lots.sorted().toList()
