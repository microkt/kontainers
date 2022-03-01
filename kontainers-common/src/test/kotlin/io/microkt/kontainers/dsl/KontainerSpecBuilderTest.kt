package io.microkt.kontainers.dsl

import io.microkt.kontainers.domain.KontainerPort
import io.microkt.kontainers.domain.KontainerSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@Tag("unit")
internal class KontainerSpecBuilderTest {

    companion object Constants {
        const val NAME = "example"
        const val IMAGE = "example.com/example:stable"
        val COMMAND = listOf("foo", "bar")
        val ENV: Map<String, String> = mapOf(
            "EXAMPLE_PROP" to "example"
        )
    }

    private val spec = kontainerSpec {
        name = NAME
        image = IMAGE
        command = COMMAND
        environment {
            ENV.entries.forEach { (k, v) -> set(k to v) }
        }
        ports {
            expose tcp 80
            expose udp 53
        }
    }

    private fun validate(kontainerSpec: KontainerSpec) {
        assertEquals(NAME, kontainerSpec.name, "Kontainer spec name should be assigned")
        assertEquals(IMAGE, kontainerSpec.image, "Kontainer spec image should be assigned")

        assertEquals(2, kontainerSpec.ports.size, "Kontainer spec expose two ports")
        assertEquals(KontainerPort.udp(53), kontainerSpec.ports.first { it.protocol == KontainerPort.Protocol.UDP })
        assertEquals(KontainerPort.tcp(80), kontainerSpec.ports.first { it.protocol == KontainerPort.Protocol.TCP })

        assertEquals(COMMAND, kontainerSpec.command, "Kontainer spec have command")
        assertEquals(ENV, kontainerSpec.environment, "Kontainer spec have environment")
    }

    @Test
    fun dsl() = validate(spec)

    @Test
    fun dslWithBaseSpec() = validate(kontainerSpec(spec) { })

    @Test
    fun dslWithBaseSpecAdditive() {
        val extSpec = kontainerSpec(spec) {
            environment {
                set("FOO" to "BAR")
            }
            ports {
                expose tcp 9999
            }
        }

        assertEquals(2, extSpec.environment.size)
        assertEquals(3, extSpec.ports.size)
    }

    @Test
    fun throwsOnMissingName() {
        assertThrows<NullPointerException> {
            kontainerSpec {
                image = IMAGE
                ports {
                    expose tcp 80
                }
            }
        }
    }

    @Test
    fun throwsOnMissingImage() {
        assertThrows<NullPointerException> {
            kontainerSpec {
                name = NAME
                ports {
                    expose tcp 80
                }
            }
        }
    }

    @Test
    fun throwsOnMissingPorts() {
        assertThrows<IllegalStateException> {
            kontainerSpec {
                name = NAME
                image = IMAGE
            }
        }
    }
}
