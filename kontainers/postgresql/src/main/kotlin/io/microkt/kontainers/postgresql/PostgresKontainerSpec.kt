package io.microkt.kontainers.postgresql

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.postgresql.PostgresKontainer.Env.POSTGRES_DB
import io.microkt.kontainers.postgresql.PostgresKontainer.Env.POSTGRES_PASSWORD
import io.microkt.kontainers.postgresql.PostgresKontainer.Env.POSTGRES_USER

/**
 * Provides a Postgres [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec].
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.postgresql.postgresKontainerSpec]
 */
val postgresKontainerSpec = kontainerSpec {
    name = "postgres"
    image = "postgres:13.4"
    command = listOf("postgres", "-c", "fsync=off")
    environment {
        set(POSTGRES_DB to "test")
        set(POSTGRES_USER to "test")
        set(POSTGRES_PASSWORD to "test")
    }
    ports {
        expose tcp 5432
    }
    resources {
        limit memory 256.MB
    }
}
