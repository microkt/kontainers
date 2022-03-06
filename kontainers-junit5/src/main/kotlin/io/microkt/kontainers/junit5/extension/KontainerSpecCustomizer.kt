package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride

class KontainerSpecCustomizer(private val kontainerSpec: KontainerSpec) {
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
