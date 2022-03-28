package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("unit")
internal class KontainerSpecCustomizerTest {

    private val spec = kontainerSpec {
        name = "foo"
        image = "foo:1"
        environment {
            set("FOO" to "BAR")
        }
        ports {
            expose tcp 80
        }
        resources {
            limit memory 10.MB
        }
    }

    private val customizer = KontainerSpecCustomizer(spec)

    @Test
    fun customizeImageOnly() {
        val override = customizer.customize(KontainerSpecOverride(image = "foo:2", environment = arrayOf(), memory = 0u))
        assertEquals("foo:2", override.image)
        assertEquals(spec.environment, override.environment)
        assertEquals(10.MB, override.resources.memory)
    }

    @Test
    fun customizeEnvironmentOnly() {
        val override = customizer.customize(KontainerSpecOverride(image = "", environment = arrayOf("BAR=baz"), memory = 0u))
        val expectedEnv = spec.environment.toMutableMap().also { it["BAR"] = "baz" }
        assertEquals("foo:1", override.image)
        assertEquals(expectedEnv, override.environment)
        assertEquals(10.MB, override.resources.memory)
    }

    @Test
    fun customizeEnvironmentReplaceValue() {
        val override = customizer.customize(KontainerSpecOverride(image = "", environment = arrayOf("FOO=baz"), memory = 0u))
        val expectedEnv = spec.environment.toMutableMap().also { it["FOO"] = "baz" }
        assertEquals("foo:1", override.image)
        assertEquals(expectedEnv, override.environment)
        assertEquals(10.MB, override.resources.memory)
    }

    @Test
    fun customizeResourcesOnly() {
        val override = customizer.customize(KontainerSpecOverride(image = "", environment = arrayOf(), memory = 15.MB))
        assertEquals("foo:1", override.image)
        assertEquals(spec.environment, override.environment)
        assertEquals(15.MB, override.resources.memory)
    }
}
