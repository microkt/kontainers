package io.microkt.kontainers.junit5.annotation

annotation class KontainerSpecOverride(
    val image: String = "",
    val environment: Array<String> = []
)
