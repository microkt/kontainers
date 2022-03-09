package io.microkt.kontainers.domain

/**
 * Provides a platform-agnostic specification for running OCI image based
 * containers across various backends.
 *
 * @author Scott Rossillo
 */
data class KontainerSpec(
    val name: String,
    val image: String,
    val command: List<String> = listOf(),
    val environment: Map<String, String> = mapOf(),
    val ports: List<KontainerPort>
)
