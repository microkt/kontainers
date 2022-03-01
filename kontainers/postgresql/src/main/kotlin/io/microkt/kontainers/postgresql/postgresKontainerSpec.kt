package io.microkt.kontainers.postgresql

import io.microkt.kontainers.dsl.kontainerSpec

private const val FSYNC_OFF_OPTION = "fsync=off"

val postgresKontainerSpec = kontainerSpec {
    name = "postgres"
    image = "postgres:${PostgresDefaults.VERSION}"
    command = listOf("postgres", "-c", FSYNC_OFF_OPTION)
    environment {
        set("POSTGRES_DB" to PostgresDefaults.DATABASE_NAME)
        set("POSTGRES_USER" to PostgresDefaults.USERNAME)
        set("POSTGRES_PASSWORD" to PostgresDefaults.PASSWORD)
    }
    ports {
        expose tcp PostgresDefaults.PORT
    }
}
