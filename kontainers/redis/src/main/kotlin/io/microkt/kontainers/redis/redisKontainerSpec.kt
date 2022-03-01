package io.microkt.kontainers.redis

import io.microkt.kontainers.dsl.kontainerSpec

val redisKontainerSpec = kontainerSpec {
    name = "redis"
    image = "redis:${RedisKontainer.VERSION}"
    ports {
        expose tcp RedisKontainer.PORT
    }
}
