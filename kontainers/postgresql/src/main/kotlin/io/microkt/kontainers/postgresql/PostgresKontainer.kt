package io.microkt.kontainers.postgresql

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

/**
 * Provides a Postgres [Kontainer].
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.postgresql.PostgresKontainerTest]
 */
class PostgresKontainer(
    override val kontainerSpec: KontainerSpec,
    parentKontainer: Kontainer
) : JdbcKontainer, GenericTcpKontainer(kontainerSpec, parentKontainer) {

    /**
     * JDBC driver `org.postgresql.Driver`.
     */
    override val driverClassName = "org.postgresql.Driver"

    override fun createJdbcUrl(): String =
        "jdbc:postgresql://${this.getAddress()}:${this.getPort()}/${kontainerSpec.environment[POSTGRES_DB]}"

    override fun getUsername(): String = kontainerSpec.environment[POSTGRES_USER]!!

    override fun getPassword(): String = kontainerSpec.environment[POSTGRES_PASSWORD]!!

    companion object Env {
        const val POSTGRES_DB = "POSTGRES_DB"
        const val POSTGRES_USER = "POSTGRES_USER"
        const val POSTGRES_PASSWORD = "POSTGRES_PASSWORD"
    }
}
