package io.microkt.kontainers.runner

import io.kubernetes.client.openapi.ApiException
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.dsl.kontainerSpec
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class KontainerRunnerFactoryTest {

    private val log = KotlinLogging.logger { }

    private val spec = kontainerSpec {
        name = "nginx"
        image = "nginx:stable-alpine"
        ports {
            expose tcp 80
        }
    }

    private val runner = KontainerRunnerFactory.createRunner()

    @Test
    fun runKontainer() = runBlocking {
        var container: Kontainer? = null

        try {
            container = runner.create(spec)
            container.start(10_000)
            assertNotNull(container.getAddress())
            assertNotNull(container.getPort())
        } catch (e: ApiException) {
            log.error { "Error starting k8s api client ${e.message}" }
            log.error { e.responseHeaders }
            log.error { e.responseBody }
            log.error("API client stack:", e)
        } finally {
            container?.remove()
        }
    }
}
