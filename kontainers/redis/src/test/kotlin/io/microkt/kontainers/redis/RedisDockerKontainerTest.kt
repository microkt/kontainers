package io.microkt.kontainers.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.microkt.kontainers.runner.KontainerRunnerFactory
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("docker")
internal class RedisDockerKontainerTest {
    companion object {
        private val redisFactory = RedisKontainerFactory()
        lateinit var redis: RedisKontainer
        lateinit var client: RedisClient

        @BeforeAll
        @JvmStatic
        fun setUp() {
            redis = redisFactory.createKontainer()
            redis.startSync()
            client = RedisClient.create(RedisURI.create(redis.getAddress(), redis.getPort()!!))

            //
            val runner = KontainerRunnerFactory.createRunner()
            val r = runner.createSync(redisKontainerSpec)
            r.startSync()
            r.getAddress()
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
        Assertions.assertNotNull(redis.getPort(redisKontainerSpec.ports.first().port))
    }
}
