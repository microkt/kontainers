package io.microkt.kontainers.docker.runner

import io.microkt.kontainers.docker.client.DockerExternalApiClient
import io.microkt.kontainers.docker.client.DockerFacade
import io.microkt.kontainers.docker.domain.DockerKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner
import mu.KotlinLogging

/**
 * Docker [KontainerRunner] implementation.
 *
 * @author Scott Rossillo
 */
class DockerKontainerRunner(
    private val dockerFacade: DockerFacade = DockerExternalApiClient.create()
) : KontainerRunner {
    private val log = KotlinLogging.logger { }

    override suspend fun create(kontainerSpec: KontainerSpec): Kontainer {
        log.debug { "Will request Docker container: $kontainerSpec" }

        if (!dockerFacade.imageExists(kontainerSpec.image)) {
            log.debug { "Docker image ${kontainerSpec.image} not found, pulling... " }
            val pullResponseItem = dockerFacade.pull(kontainerSpec.image)
            log.debug { "Pulled Docker image $pullResponseItem" }
        }

        val result = dockerFacade.create(kontainerSpec)
        log.debug { "Created Docker container: $result" }
        return DockerKontainer(id = result.id, kontainerSpec = kontainerSpec, dockerKontainerRunner = this)
    }

    override suspend fun delete(kontainerId: String) {
        log.debug { "Stopping container $kontainerId" }
        dockerFacade.stop(kontainerId)
        log.debug { "Deleting container $kontainerId" }
        dockerFacade.delete(kontainerId)
    }

    suspend fun inspect(kontainerId: String) =
        dockerFacade.inspect(kontainerId)

    override suspend fun start(kontainerId: String) {
        log.debug { "Starting container $kontainerId" }
        val info = dockerFacade.start(kontainerId)
        log.debug { "Started container $info" }
    }
}
