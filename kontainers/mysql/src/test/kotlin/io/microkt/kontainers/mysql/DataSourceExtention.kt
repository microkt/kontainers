package io.microkt.kontainers.mysql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.microkt.kontainers.domain.JdbcKontainer
import javax.sql.DataSource

internal fun buildHikariDataSource(jdbcKontainer: JdbcKontainer): DataSource {
    val hikariConfig = HikariConfig()
    hikariConfig.jdbcUrl = jdbcKontainer.createJdbcUrl()
    hikariConfig.username = jdbcKontainer.getUsername()
    hikariConfig.password = jdbcKontainer.getPassword()
    return HikariDataSource(hikariConfig)
}
