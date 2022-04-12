package io.microkt.kontainers.redis.spring

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.redis.RedisKontainer

/**
 * Spring Boot Redis [PropertySupplier].
 *
 * @author Scott Rossillo
 *
 * @sample io.microkt.kontainers.redis.RedisPropertySupplierTest
 */
class SpringBootRedisPropertySupplier : PropertySupplier {
    override fun supply(kontainer: Kontainer): Map<String, String> =
        when (kontainer) {
            is RedisKontainer -> redisProps(kontainer)
            else -> mapOf()
        }

    private fun redisProps(kontainer: RedisKontainer): Map<String, String> =
        mapOf(
            "spring.redis.host" to kontainer.getAddress()!!,
            "spring.redis.port" to kontainer.getPort()!!.toString()
        )
}
