package io.github.eutkin.rtb.ssp

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("io.github.eutkin.rtb")
            .start()
}

