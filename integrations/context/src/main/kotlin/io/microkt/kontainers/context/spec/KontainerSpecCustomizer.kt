package io.microkt.kontainers.context.spec

import io.microkt.kontainers.domain.KontainerSpec

/**
 * Applies customizations to a [KontainerSpec] created with [KontainerSpecOverride]
 * annotations.
 *
 * @author Scott Rossillo
 * @see KontainerSpecOverride
 */
class KontainerSpecCustomizer(
    private val kontainerSpec: KontainerSpec
) {
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
        kontainerSpecOverride
            .value
            .constructors
            .first { it.parameters.isEmpty() }
            .call()
            .override(kontainerSpec)
}
