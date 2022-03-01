package io.microkt.kontainers.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.microkt.kontainers.kubernetes.runner.KubernetesKontainerRunner
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("kubernetes")
internal class RedisKubernetesKontainerTest {
    companion object {
        lateinit var redis: RedisKontainer
        lateinit var client: RedisClient

        @BeforeAll
        @JvmStatic
        fun setUp() {
            redis = RedisKontainerFactory().createKontainer(redisKontainerSpec, KubernetesKontainerRunner())
            redis.startSync()
            client = RedisClient.create(RedisURI.create(redis.getAddress(), redis.getPort()!!))
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            redis.removeSync()
        }
    }

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
