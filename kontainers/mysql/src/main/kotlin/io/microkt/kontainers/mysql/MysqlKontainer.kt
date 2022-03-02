package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class MysqlKontainer(
    override val kontainerSpec: KontainerSpec,
    private val parentHandle: Kontainer
) : JdbcKontainer, GenericTcpKontainer(kontainerSpec, parentHandle) {

    override val driverClassName = MysqlDefaults.DATABASE_DRIVER

    override fun createJdbcUrl(): String =
        "jdbc:mysql://${this.getAddress()}:${this.getPort()}/${MysqlDefaults.DATABASE_NAME}"

    override fun getUsername(): String = kontainerSpec.environment["MYSQL_USER"]!!

    override fun getPassword(): String = kontainerSpec.environment["MYSQL_PASSWORD"]!!
}
