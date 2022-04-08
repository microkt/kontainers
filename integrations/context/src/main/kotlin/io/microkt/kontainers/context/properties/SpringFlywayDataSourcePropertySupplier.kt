package io.microkt.kontainers.context.properties

import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer

object SpringFlywayDataSourcePropertySupplier : PropertySupplier {
    override fun supply(kontainer: Kontainer): Map<String, String> =
        when (kontainer is JdbcKontainer) {
            true -> supplyProps(kontainer)
            false -> mapOf()
        }

    private fun supplyProps(kontainer: JdbcKontainer): Map<String, String> =
        mapOf(
            "spring.flyway.url" to kontainer.createJdbcUrl(),
            "spring.flyway.user" to kontainer.getUsername(),
            "spring.flyway.password" to kontainer.getPassword()
        )
}
