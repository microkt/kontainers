package io.microkt.kontainers.redis

import io.microkt.kontainers.dsl.kontainerSpec

/**
 * Default Kontainer specification for Redis.
 *
 * @author Scott Rossillo
 */
val redisKontainerSpec = kontainerSpec {
    name = "redis"
    image = "redis:6.2-alpine"
    ports {
        expose tcp 6379
    }
}
