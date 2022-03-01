package io.microkt.kontainers.postgresql

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class PostgresKontainer(
    override val kontainerSpec: KontainerSpec,
    private val parentHandle: Kontainer
) : JdbcKontainer, GenericTcpKontainer(kontainerSpec, parentHandle) {

    override val driverClassName = PostgresDefaults.DATABASE_DRIVER

    override fun createJdbcUrl(): String =
        "jdbc:postgresql://${this.getAddress()}:${this.getPort()}/${PostgresDefaults.DATABASE_NAME}"

    override fun getUsername(): String = kontainerSpec.environment["POSTGRES_USER"]!!

    override fun getPassword(): String = kontainerSpec.environment["POSTGRES_PASSWORD"]!!
}
