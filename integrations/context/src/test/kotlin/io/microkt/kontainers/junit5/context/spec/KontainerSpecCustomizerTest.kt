package io.microkt.kontainers.junit5.context.spec

import io.microkt.kontainers.context.spec.KontainerSpecCustomizer
import io.microkt.kontainers.context.spec.KontainerSpecOverride
import io.microkt.kontainers.context.spec.KontainerSpecProvider
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
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

    internal class FooImageOverrideSpecProvider : KontainerSpecProvider {
        override fun override(kontainerSpec: KontainerSpec): KontainerSpec =
            kontainerSpec(kontainerSpec) {
                image = "foo:2"
            }
    }

    internal class FooEnvironmentOverrideSpecProvider : KontainerSpecProvider {
        override fun override(kontainerSpec: KontainerSpec): KontainerSpec =
            kontainerSpec(kontainerSpec) {
                environment { set("BAR" to "baz") }
            }
    }

    internal class FooEnvironmentReplaceValueSpecProvider : KontainerSpecProvider {
        override fun override(kontainerSpec: KontainerSpec): KontainerSpec =
            kontainerSpec(kontainerSpec) {
                environment { set("FOO" to "baz") }
            }
    }

    private val customizer = KontainerSpecCustomizer(spec)

    @Test
    fun customizeImageOnly() {
        val override = customizer.customize(KontainerSpecOverride(FooImageOverrideSpecProvider::class))
        assertEquals("foo:2", override.image)
        assertEquals(spec.environment, override.environment)
    }

    @Test
    fun customizeEnvironmentOnly() {
        val override = customizer.customize(KontainerSpecOverride(FooEnvironmentOverrideSpecProvider::class))
        val expectedEnv = spec.environment.toMutableMap().also { it["BAR"] = "baz" }
        assertEquals("foo:1", override.image)
        assertEquals(expectedEnv, override.environment)
    }

    @Test
    fun customizeEnvironmentReplaceValue() {
        val override = customizer.customize(KontainerSpecOverride(FooEnvironmentReplaceValueSpecProvider::class))
        val expectedEnv = spec.environment.toMutableMap().also { it["FOO"] = "baz" }
        assertEquals("foo:1", override.image)
        assertEquals(expectedEnv, override.environment)
    }
}
