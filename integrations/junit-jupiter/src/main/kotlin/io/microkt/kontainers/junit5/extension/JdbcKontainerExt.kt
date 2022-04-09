package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.JdbcKontainer
import mu.KotlinLogging
import java.sql.DriverManager

private val log = KotlinLogging.logger { }

fun JdbcKontainer.isDatabaseReady(): Boolean =
    try {
        DriverManager.getConnection(this.createJdbcUrl(), this.getUsername(), this.getPassword()).use { true }
    } catch (e: Exception) {
        log.info { "Database not yet ready: ${e.message}" }
        false
    }
