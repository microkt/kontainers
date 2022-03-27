package io.microkt.kontainers.redis

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a [RedisKontainer] [KontainerFactory].
 *
 * @author Scott Rossillo
 */
class RedisKontainerFactory : AbstractKontainerFactory<RedisKontainer>(), KontainerFactory<RedisKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = redisKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): RedisKontainer =
        RedisKontainer(
            kontainerSpec = kontainerSpec,
            parent = runner.createSync(kontainerSpec)
        )

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == RedisKontainer::class
}
