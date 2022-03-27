package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * Provides a [Kontainer] for [MySQL](https://www.mysql.com/).
 *
 * @author Scott Rossillo
 * @constructor Creates a new [MysqlKontainer] with the given [kontainerSpec]
 * and parent [Kontainer].
 * @sample [io.microkt.kontainers.mysql.MysqlKontainerTest]
 */
class MysqlKontainer(
    override val kontainerSpec: KontainerSpec,
    parentKontainer: Kontainer
) : JdbcKontainer, GenericTcpKontainer(kontainerSpec, parentKontainer) {

    /**
     * JDBC driver `com.mysql.jdbc.driver`.
     */
    override val driverClassName = "com.mysql.jdbc.driver"

    override fun createJdbcUrl(): String =
        "jdbc:mysql://${this.getAddress()}:${this.getPort()}/${this.getDatabaseName()}"

    override fun getDatabaseName(): String =
        kontainerSpec.environment[Env.MYSQL_DATABASE]!!

    override fun getUsername(): String = kontainerSpec.environment[Env.MYSQL_USER]!!

    override fun getPassword(): String = kontainerSpec.environment[Env.MYSQL_PASSWORD]!!

    /**
     * MySQL Kontainer environment variables.
     */
    object Env {
        const val MYSQL_DATABASE = "MYSQL_DATABASE"
        const val MYSQL_USER = "MYSQL_USER"
        const val MYSQL_PASSWORD = "MYSQL_PASSWORD"
        const val MYSQL_ROOT_PASSWORD = "MYSQL_ROOT_PASSWORD"
    }

    /**
     * Waits for the TCP socket to be ready and delays an extra 10
     * seconds for MySQL to configure and restart itself.
     *
     * TODO: Use MySQL driver or wire protocol to check if the db is up.
     */
    override fun waitForReady(timeout: Long) {
        super.waitForReady(timeout)
        runBlocking { delay(15.seconds.toJavaDuration()) }
    }
}
