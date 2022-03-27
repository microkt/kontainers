package io.microkt.kontainers.docker.runner

import io.microkt.kontainers.docker.client.DockerFacade
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.KontainerSpecResources
import io.microkt.kontainers.domain.MB
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("unit")
internal class DockerKontainerRunnerTest {

    companion object {
        const val KONTAINER_ID = "kontainer-id"
        const val IMAGE = "example.com/foo:stable"
    }

    private val dockerFacade: DockerFacade = mockk(relaxed = true)
    private val runner: DockerKontainerRunner = DockerKontainerRunner(dockerFacade)

    private val kontainerSpec = KontainerSpec(
        name = "foo",
        image = IMAGE,
        ports = listOf(),
        resources = KontainerSpecResources(memory = 128.MB)
    )

    @Test
    fun createImageExists() = runBlocking {
        coEvery { dockerFacade.imageExists(IMAGE) } returns true
        runner.create(kontainerSpec)
        coVerify {
            dockerFacade.imageExists(IMAGE)
            dockerFacade.create(kontainerSpec)
        }
        confirmVerified(dockerFacade)
    }

    @Test
    fun createImageNotExists() = runBlocking {
        coEvery { dockerFacade.imageExists(IMAGE) } returns false
        runner.create(kontainerSpec)
        coVerify {
            dockerFacade.imageExists(IMAGE)
            dockerFacade.pull(IMAGE)
            dockerFacade.create(kontainerSpec)
        }
        confirmVerified(dockerFacade)
    }

    @Test
    fun delete() = runBlocking {
        runner.delete(kontainerId = KONTAINER_ID)
        coVerify {
            dockerFacade.stop(KONTAINER_ID)
            dockerFacade.delete(KONTAINER_ID)
        }
        confirmVerified(dockerFacade)
    }

    @Test
    fun inspect() = runBlocking {
        runner.inspect(kontainerId = KONTAINER_ID)
        coVerify { dockerFacade.inspect(KONTAINER_ID) }
        confirmVerified(dockerFacade)
    }

    @Test
    fun start() = runBlocking {
        runner.start(kontainerId = KONTAINER_ID)
        coVerify { dockerFacade.start(KONTAINER_ID) }
        confirmVerified(dockerFacade)
    }
}
