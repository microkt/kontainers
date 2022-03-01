package io.microkt.kontainers.postgresql

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner
import io.microkt.kontainers.runner.KontainerRunnerFactory
import kotlin.reflect.KClass

class PostgresKontainerFactory : KontainerFactory<PostgresKontainer> {
    private val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }

    override val kontainerSpec: KontainerSpec
        get() = postgresKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): PostgresKontainer {
        return PostgresKontainer(
            kontainerSpec = kontainerSpec,
            parentHandle = runner.createSync(kontainerSpec)
        )
    }

    override fun createKontainer(): PostgresKontainer = createKontainer(kontainerSpec)

    fun createKontainer(kontainerSpec: KontainerSpec, runner: KontainerRunner): PostgresKontainer {
        val handle = runner.createSync(kontainerSpec)
        return PostgresKontainer(
            kontainerSpec = kontainerSpec,
            parentHandle = handle
        )
    }

    override fun supports(kontainerKClass: KClass<Kontainer>): Boolean =
        kontainerKClass == PostgresKontainer::class
}
