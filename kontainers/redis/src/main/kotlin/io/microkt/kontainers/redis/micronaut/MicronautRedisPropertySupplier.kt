package io.microkt.kontainers.redis.micronaut

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.redis.RedisKontainer

/**
 * Micronaut Redis [PropertySupplier].
 *
 * @author Scott Rossillo
 *
 * @sample io.microkt.kontainers.redis.RedisPropertySupplierTest
 */
class MicronautRedisPropertySupplier : PropertySupplier {
    override fun supply(kontainer: Kontainer): Map<String, String> =
        when (kontainer) {
            is RedisKontainer -> redisProps(kontainer)
            else -> mapOf()
        }

    private fun redisProps(kontainer: RedisKontainer): Map<String, String> =
        mapOf(
            "redis.uri" to "redis://${kontainer.getAddress()}/${kontainer.getPort()}"
        )
}
