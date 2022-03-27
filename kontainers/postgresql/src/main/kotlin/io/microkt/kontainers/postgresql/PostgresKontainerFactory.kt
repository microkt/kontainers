package io.microkt.kontainers.postgresql

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a Postgres [KontainerFactory].
 *
 * @author Scott Rossillo
 */
class PostgresKontainerFactory : AbstractKontainerFactory<PostgresKontainer>(), KontainerFactory<PostgresKontainer> {

    override val kontainerSpec: KontainerSpec
        get() = postgresKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): PostgresKontainer {
        return PostgresKontainer(
            kontainerSpec = kontainerSpec,
            parentKontainer = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == PostgresKontainer::class
}
