package io.microkt.kontainers.junit5

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride
import io.microkt.kontainers.junit5.annotation.Kontainers
import io.microkt.kontainers.redis.RedisKontainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class KontainerExtensionTest(
    private val redis: RedisKontainer,
    @KontainerSpecOverride(RedisBusterSpecProvider::class)
    private val redis2: RedisKontainer
) {
    private val client: RedisClient = RedisClient.create(RedisURI.create(redis.getAddress(), redis.getPort()!!))
    private val client2: RedisClient = RedisClient.create(RedisURI.create(redis2.getAddress(), redis2.getPort()!!))

    @Test
    fun testContainerImages() {
        assertEquals("redis:6.2-alpine", redis.kontainerSpec.image)
        assertEquals("redis:6.2-buster", redis2.kontainerSpec.image)
    }

    @Test
    fun testHashOps() {
        val commands = client.connect().sync()
        commands.hset(HASH_KEY, "foo", "bar")
        assertEquals(1, commands.hlen(HASH_KEY), "Hash should contain one key")
        assertEquals(0, client2.connect().sync().hlen(HASH_KEY), "Second redis should not contain data")
    }

    companion object {
        const val HASH_KEY = "kontainers"
    }
}
