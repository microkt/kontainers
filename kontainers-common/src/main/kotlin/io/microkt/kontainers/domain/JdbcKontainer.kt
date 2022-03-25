package io.microkt.kontainers.domain

/**
 * Provides JDBC specific extensions to [Kontainer]'s interface.
 *
 * @author Scott Rossillo
 */
interface JdbcKontainer : DatabaseKontainer {
    /**
     * Name of the JDBC driver used to connect to this Kontainer's database.
     */
    val driverClassName: String

    /**
     * Returns a JDBC URL for connecting to this Kontainer's database.
     */
    fun createJdbcUrl(): String
}
