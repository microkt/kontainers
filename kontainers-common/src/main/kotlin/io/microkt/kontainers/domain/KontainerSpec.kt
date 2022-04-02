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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KontainerSpec

        if (name != other.name) return false
        if (image != other.image) return false
        if (command != other.command) return false
        if (environment != other.environment) return false
        if (ports != other.ports) return false
        if (resources != other.resources) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + command.hashCode()
        result = 31 * result + environment.hashCode()
        result = 31 * result + ports.hashCode()
        result = 31 * result + resources.hashCode()
        return result
    }
}
