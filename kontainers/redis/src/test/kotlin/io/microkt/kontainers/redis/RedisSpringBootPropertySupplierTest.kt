package io.microkt.kontainers.redis

import io.microkt.kontainers.junit5.annotation.Kontainer
import io.microkt.kontainers.redis.spring.SpringBootRedisPropertySupplier
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Kontainer(RedisKontainer::class, propertySuppliers = [SpringBootRedisPropertySupplier::class])
@Tags(
    Tag("kubernetes"),
    Tag("docker")
)
internal class RedisSpringBootPropertySupplierTest {
    @Test
    fun testProps() {
        assertNotNull(System.getProperty("spring.redis.host"))
        assertNotNull(System.getProperty("spring.redis.port"))
    }
}
