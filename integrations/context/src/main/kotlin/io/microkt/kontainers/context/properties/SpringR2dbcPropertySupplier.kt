package io.microkt.kontainers.context.properties

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.R2dbcKontainer
import mu.KotlinLogging

object SpringR2dbcPropertySupplier : PropertySupplier {
    private const val PROP_PREFIX = "spring.r2dbc"
    private val log = KotlinLogging.logger { }
    override fun supply(kontainer: Kontainer): Map<String, String> =
        if (kontainer is R2dbcKontainer) {
            log.info { "Exporting $PROP_PREFIX properties" }
            mapOf(
                "$PROP_PREFIX.url" to kontainer.createR2dbcUrl(),
                "$PROP_PREFIX.username" to kontainer.getUsername(),
                "$PROP_PREFIX.password" to kontainer.getPassword()
            )
        } else {
            mapOf()
        }
}
