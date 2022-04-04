package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride

/**
 * Applies customizations to a [KontainerSpec] created with [KontainerSpecOverride]
 * annotations.
 *
 * @author Scott Rossillo
 * @see KontainerSpecOverride
 */
internal class KontainerSpecCustomizer(private val kontainerSpec: KontainerSpec) {
    /**
     * Applies overrides from the given [kontainerSpecOverride] to the
     * [io.microkt.kontainers.domain.Kontainer] annotated with the given
     * [KontainerSpecOverride].
     *
     * Image overrides must include the image version or `latest` is assumed.
     *
     * Environment overrides are merged with the [kontainerSpec]'s existing
     * [KontainerSpec.environment].
     */
    fun customize(kontainerSpecOverride: KontainerSpecOverride): KontainerSpec =
        kontainerSpec.copy(
            image = kontainerSpecOverride.image.ifBlank { kontainerSpec.image },
            environment = kontainerSpec.environment.toMutableMap().also {
                kontainerSpecOverride.environment.forEach { e ->
                    val (name, value) = e.split('=', limit = 2)
                    it[name] = value
                }
            }
        )
}
