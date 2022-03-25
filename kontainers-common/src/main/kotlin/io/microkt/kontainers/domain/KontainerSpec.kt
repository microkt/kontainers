package io.microkt.kontainers.domain

/**
 * Provides a platform-agnostic specification for running OCI image based
 * containers across various backends.
 *
 * @author Scott Rossillo
 * @see io.microkt.kontainers.dsl.KontainerDsl
 */
data class KontainerSpec(

    /**
     * Kontainer specification name.
     */
    val name: String,

    /**
     * The OCI image name and version in standard Docker form.
     */
    val image: String,

    /**
     * The Docker entrypoint and Kubernetes command.
     */
    val command: List<String> = listOf(),

    /**
     * Map of environment variables to pass to the Kontainer on startup.
     */
    val environment: Map<String, String> = mapOf(),

    /**
     * A list of ports to expose on the Kontainer.
     */
    val ports: List<KontainerPort>,

    val resources: KontainerSpecResources
)
