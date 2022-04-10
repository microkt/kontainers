package io.microkt.kontainers.mariadb

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * Provides a [Kontainer] for [MySQL](https://www.mysql.com/).
 *
 * @author Scott Rossillo
 * @constructor Creates a new [MariaKontainer] with the given [kontainerSpec]
 * and delegate [PlatformKontainer].
 * @sample [io.microkt.kontainers.mariadb.mariaKontainerSpec]
 */
class MariaKontainer(
    override val kontainerSpec: KontainerSpec,
    delegate: PlatformKontainer
) : JdbcKontainer, GenericTcpKontainer(kontainerSpec, delegate) {

    /**
     * MariaDB JDBC driver `org.mariadb.jdbc.Driver`
     */
    override val driverClassName = "org.mariadb.jdbc.Driver"

    override fun createJdbcUrl(): String =
        "jdbc:mariadb://${this.getAddress()}:${this.getPort()}/${this.getDatabaseName()}"

    override fun getDatabaseName(): String =
        kontainerSpec.environment[Env.MARIADB_DATABASE]!!

    override fun getUsername(): String = kontainerSpec.environment[Env.MARIADB_USER]!!

    override fun getPassword(): String = kontainerSpec.environment[Env.MARIADB_PASSWORD]!!

    /**
     * MySQL Kontainer environment variables.
     */
    object Env {
        const val MARIADB_DATABASE = "MARIADB_DATABASE"
        const val MARIADB_USER = "MARIADB_USER"
        const val MARIADB_PASSWORD = "MARIADB_PASSWORD"
        const val MARIADB_ROOT_PASSWORD = "MARIADB_ROOT_PASSWORD"
    }

    /**
     * Waits for the TCP socket to be ready and delays an extra 10
     * seconds for MySQL to configure and restart itself.
     *
     * TODO: Use MariaDB driver or wire protocol to check if the db is up.
     */
    override fun waitForReady(timeout: Long) {
        super.waitForReady(timeout)
        runBlocking { delay(10.seconds.toJavaDuration()) }
    }
}
