package io.microkt.kontainers.micronaut.properties

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer

/**
 * Micronaut [PropertySupplier] for the default datasource.
 *
 * Populates properties under `datasources.default` for a
 * [DatabaseKontainer][io.microkt.kontainers.junit5.annotation.DatabaseKontainer].
 *
 * @author Scott Rossillo
 * @see io.microkt.kontainers.junit5.annotation.DatabaseKontainer
 */
class MicronautDataSourcePropertySupplier : PropertySupplier {
    override fun supply(kontainer: Kontainer): Map<String, String> =
        when (kontainer is JdbcKontainer) {
            true ->
                mapOf(
                    "datasources.default.url" to kontainer.createJdbcUrl(),
                    "datasources.default.username" to kontainer.getUsername(),
                    "datasources.default.password" to kontainer.getPassword(),
                    "datasources.default.driverClassName" to kontainer.driverClassName
                )
            false -> mapOf()
        }
}
