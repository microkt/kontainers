package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class MysqlKontainer(
    override val kontainerSpec: KontainerSpec,
    private val parentHandle: Kontainer
) : JdbcKontainer, GenericTcpKontainer(kontainerSpec, parentHandle) {

    override val driverClassName = Default.DATABASE_DRIVER

    override fun createJdbcUrl(): String =
        "jdbc:mysql://${this.getAddress()}:${this.getPort()}/${Default.DATABASE_NAME}"

    override fun getUsername(): String = kontainerSpec.environment[Env.MYSQL_USER]!!

    override fun getPassword(): String = kontainerSpec.environment[Env.MYSQL_PASSWORD]!!

    object Default {
        const val IMAGE = "mysql"
        const val PORT = 3306
        const val DATABASE_DRIVER = "com.mysql.jdbc.driver"
        const val DATABASE_NAME = "test"
        const val USERNAME = "test"
        const val PASSWORD = "test"
        const val VERSION = "8.0"
    }

    object Env {
        const val MYSQL_DATABASE = "MYSQL_DATABASE"
        const val MYSQL_USER = "MYSQL_USER"
        const val MYSQL_PASSWORD = "MYSQL_PASSWORD"
        const val MYSQL_ROOT_PASSWORD = "MYSQL_ROOT_PASSWORD"
    }
}
