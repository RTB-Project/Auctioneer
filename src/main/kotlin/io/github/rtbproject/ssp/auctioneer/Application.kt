package io.github.rtbproject.ssp.auctioneer

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("io.github.rtbproject")
            .start()
}

