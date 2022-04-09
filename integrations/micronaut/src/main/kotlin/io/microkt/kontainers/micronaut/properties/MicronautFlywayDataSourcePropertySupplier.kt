package io.microkt.kontainers.micronaut.properties

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import mu.KotlinLogging

class MicronautFlywayDataSourcePropertySupplier : PropertySupplier {
    private val log = KotlinLogging.logger { }
    override fun supply(kontainer: Kontainer): Map<String, String> =
        when (kontainer is JdbcKontainer) {
            true -> {
                log.info { "Exporting Spring Flyway DataSource properties" }
                supplyProps(kontainer)
            }
            false -> mapOf()
        }

    private fun supplyProps(kontainer: JdbcKontainer): Map<String, String> =
        mapOf(
            "flyway.datasources.default.enabled" to "true"
        )
}
