package io.microkt.kontainers.postgresql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

internal fun buildDataSource(postgres: PostgresKontainer): DataSource {
    val hikariConfig = HikariConfig()
    hikariConfig.jdbcUrl = postgres.createJdbcUrl()
    hikariConfig.username = postgres.getUsername()
    hikariConfig.password = postgres.getPassword()
    hikariConfig.driverClassName = postgres.driverClassName
    return HikariDataSource(hikariConfig)
}
