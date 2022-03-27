package io.microkt.kontainers.redis

import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.junit5.annotation.Kontainers
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags

@Kontainers
@Tags(
    Tag("kubernetes"),
    Tag("docker")
)
internal class CustomRedisKontainerTest {

    private val spec = kontainerSpec(redisKontainerSpec) {
        image = "foo:bar"
        environment {
            set("FOO" to "BAR")
        }
    }

    init {
        val kontainer = RedisKontainerFactory().createKontainer(spec)
        kontainer.startSync()
    }
}
