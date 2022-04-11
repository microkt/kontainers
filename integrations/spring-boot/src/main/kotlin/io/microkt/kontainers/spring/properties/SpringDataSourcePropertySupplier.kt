package io.microkt.kontainers.spring.properties

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer

/**
 * [Property supplier][PropertySupplier] for Sping Boot Data Source.
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.spring.KontainersDemoApplicationTests
 */
class SpringDataSourcePropertySupplier : PropertySupplier {
    override fun supply(kontainer: Kontainer): Map<String, String> =
        when (kontainer is JdbcKontainer) {
            true ->
                mapOf(
                    "spring.datasource.url" to kontainer.createJdbcUrl(),
                    "spring.datasource.username" to kontainer.getUsername(),
                    "spring.datasource.password" to kontainer.getPassword()
                )
            false -> mapOf()
        }
}
