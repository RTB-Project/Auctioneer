package io.github.rtbproject.ssp.auctioneer.impl

import reactor.util.function.Tuple2
import reactor.util.function.Tuple3
import java.math.BigDecimal

operator fun <T1, T2> Tuple2<T1, T2>.component1(): T1 = this.t1
operator fun <T1, T2> Tuple2<T1, T2>.component2(): T2 = this.t2

operator fun <T1, T2, T3> Tuple3<T1, T2, T3>.component1(): T1 = this.t1
operator fun <T1, T2, T3> Tuple3<T1, T2, T3>.component2(): T2 = this.t2
operator fun <T1, T2, T3> Tuple3<T1, T2, T3>.component3(): T3 = this.t3

fun Iterable<BigDecimal>.sum() : BigDecimal {
    if (!this.iterator().hasNext()) {
        return BigDecimal.ZERO
    }
    return this.reduce{ acc, element -> acc.plus(element)}
}