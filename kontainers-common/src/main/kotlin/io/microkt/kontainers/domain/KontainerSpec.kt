package io.microkt.kontainers.domain

data class KontainerSpec(
    val name: String,
    val image: String,
    val command: List<String> = listOf(),
    val environment: Map<String, String> = mapOf(),
    val ports: List<KontainerPort>
)
