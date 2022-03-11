package io.microkt.kontainers.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.microkt.kontainers.junit5.annotation.Kontainers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Kontainers
@Tags(
    Tag("kubernetes"),
    Tag("docker")
)
internal class RedisKontainerTest(
    private val redis: RedisKontainer
) {
    private val client: RedisClient = RedisClient.create(RedisURI.create(redis.getAddress(), redis.getPort()!!))

    @Test
    fun testHashOps() {
        val commands = client.connect().sync()
        commands.hset("kontainers", "foo", "bar")
        assertEquals(1, commands.hlen("kontainers"), "Hash should contain one key")
    }

    @Test
    fun testValidPort() {
        assertNotNull(redis.getPort(redisKontainerSpec.ports.first().port))
    }
}
