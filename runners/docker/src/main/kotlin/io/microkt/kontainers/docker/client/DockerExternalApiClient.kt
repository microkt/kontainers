package io.microkt.kontainers.docker.client

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.exception.NotFoundException
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PullResponseItem
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.retryOperation
import mu.KotlinLogging
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Provides a [DockerFacade] implementation based on [DockerClient].
 *
 * @author Scott Rossillo
 */
internal class DockerExternalApiClient(private val client: DockerClient) : DockerFacade {
    private val log = KotlinLogging.logger {}

    override suspend fun create(kontainerSpec: KontainerSpec): CreateContainerResponse {
        val cmd = client.createContainerCmd(kontainerSpec.image)
            .withCmd(kontainerSpec.command)
            .withEnv(kontainerSpec.environment.map { (k, v) -> "$k=$v" })
            .withExposedPorts(kontainerSpec.exposedPorts())
            .withHostConfig(
                HostConfig
                    .newHostConfig()
                    .withPortBindings(kontainerSpec.portBindings())
                    .withMemory(kontainerSpec.resources.memory.toLong())
            )
            .withLabels(
                mapOf("io.microkt.kontainers" to "true")
            )
            .also {
                if (kontainerSpec.command.isNotEmpty()) {
                    it.withCmd(kontainerSpec.command)
                }
            }

        log.info { "Starting container with command: $cmd" }

        return cmd.exec()
    }

    override suspend fun delete(containerId: String) {
        client.removeContainerCmd(containerId).withForce(true).exec()
    } override suspend fun inspect(containerId: String): InspectContainerResponse =
        client.inspectContainerCmd(containerId).exec()

    override suspend fun imageExists(image: String): Boolean {
        return try {
            client.inspectImageCmd(image).exec()
            true
        } catch (e: NotFoundException) {
            log.debug { "Docker image $image not found" }
            false
        }
    }

    override suspend fun pull(image: String): PullResponseItem =
        suspendCoroutine { cont ->
            client.pullImageCmd(image).exec(object : ResultCallback.Adapter<PullResponseItem>() {
                private var item: PullResponseItem? = null
                override fun onNext(item: PullResponseItem?) {
                    log.debug { "$item" }
                    this.item = item
                }
                override fun onComplete() = cont.resume(item!!)
                override fun onError(throwable: Throwable?) = cont.resumeWithException(throwable!!)
            })
        }

    private fun hasExportedPorts(info: InspectContainerResponse): Boolean =
        info.networkSettings.ports.bindings.entries.none { it.value != null }

    override suspend fun start(containerId: String): InspectContainerResponse? {
        client.startContainerCmd(containerId).exec()
        return retryOperation(retries = 5, maxDelay = 400) {
            val info = inspect(containerId)
            val running: Boolean = info.state.running ?: false
            if (!running || hasExportedPorts(info)) {
                log.debug { "Waiting for container $containerId to start and expose ports" }
                operationFailed()
            } else {
                log.debug { "Container $containerId is running" }
                info
            }
        }
    }

    override suspend fun stop(containerId: String) {
        client.stopContainerCmd(containerId).exec()
    }

    companion object Factory {
        fun create(): DockerFacade {
            val config = DefaultDockerClientConfig.createDefaultConfigBuilder().build()
            val httpClient = ZerodepDockerHttpClient.Builder()
                .dockerHost(config.dockerHost)
                .build()
            val client: DockerClient = DockerClientImpl.getInstance(config, httpClient)
            return DockerExternalApiClient(client)
        }
    }
}
