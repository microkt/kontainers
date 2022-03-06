package io.microkt.kontainers.mysql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.microkt.kontainers.domain.JdbcKontainer
import javax.sql.DataSource

internal fun buildHikariDataSource(jdbcKontainer: JdbcKontainer): DataSource =
    HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = jdbcKontainer.createJdbcUrl()
            username = jdbcKontainer.getUsername()
            password = jdbcKontainer.getPassword()
        }
    )
