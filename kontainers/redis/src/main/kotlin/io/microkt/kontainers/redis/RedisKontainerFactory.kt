package io.microkt.kontainers.redis

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner
import io.microkt.kontainers.runner.KontainerRunnerFactory
import kotlin.reflect.KClass

class RedisKontainerFactory : KontainerFactory<RedisKontainer> {
    private val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }
    override val kontainerSpec: KontainerSpec
        get() = redisKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): RedisKontainer =
        RedisKontainer(
            kontainerSpec = kontainerSpec,
            parent = runner.createSync(kontainerSpec)
        )

    override fun createKontainer(): RedisKontainer = createKontainer(kontainerSpec)

    fun createKontainer(kontainerSpec: KontainerSpec, runner: KontainerRunner): RedisKontainer =
        RedisKontainer(
            kontainerSpec = kontainerSpec,
            parent = runner.createSync(kontainerSpec)
        )

    override fun supports(kontainerKClass: KClass<Kontainer>): Boolean =
        kontainerKClass == RedisKontainer::class
}
