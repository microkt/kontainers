package io.microkt.kontainers.docker.domain

import io.microkt.kontainers.docker.runner.DockerKontainerRunner
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("docker")
internal class DockerKontainerTest {

    companion object {

        private val kontainerSpec: KontainerSpec = kontainerSpec {
            name = "nginx"
            image = "nginx:stable-alpine"
            ports {
                expose tcp 80
                expose tcp 443
            }
            resources {
                limit memory 64.MB
            }
        }

        private val runner = DockerKontainerRunner()

        lateinit var kontainer: Kontainer

        @BeforeAll
        @JvmStatic
        fun setUp() = runBlocking {
            kontainer = runner.create(kontainerSpec)
            kontainer.start()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            kontainer.removeSync()
        }
    }

    @Test
    fun getPort() {
        assertNotNull(kontainer.getPort())
    }

    @Test
    fun getPortBySpec() {
        assertNotNull(kontainer.getPort(80))
        assertNotNull(kontainer.getPort(443))
    }

    @Test
    fun getAddress() {
        assertNotNull(kontainer.getAddress())
    }

    @Test
    fun getKontainerSpec() {
        assertEquals(kontainerSpec, kontainer.kontainerSpec, "Kontainer spec should match the create spec")
    }

    @Test
    fun getId() {
        assertNotNull(kontainer.id, "Kontainer ID is required")
    }
}
