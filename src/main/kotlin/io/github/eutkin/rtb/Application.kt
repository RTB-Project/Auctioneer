package io.github.eutkin.rtb

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
        info = Info(
                title = "ssp",
                version = "0.1"
        )
)
object Api {
}

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("io.github.eutkin.rtb")
            .start()
}

