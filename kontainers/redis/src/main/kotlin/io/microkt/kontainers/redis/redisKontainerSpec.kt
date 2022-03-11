package io.microkt.kontainers.redis

import io.microkt.kontainers.dsl.kontainerSpec

/**
 * Default Kontainer specification for Redis.
 *
 * @author Scott Rossillo
 */
val redisKontainerSpec = kontainerSpec {
    name = "redis"
    image = "${RedisKontainer.IMAGE}:${RedisKontainer.VERSION}"
    ports {
        expose tcp RedisKontainer.PORT
    }
}
