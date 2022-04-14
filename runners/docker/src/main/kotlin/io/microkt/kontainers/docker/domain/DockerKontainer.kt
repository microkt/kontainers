package io.microkt.kontainers.docker.domain

import com.github.dockerjava.api.command.InspectContainerResponse
import io.microkt.kontainers.docker.runner.DockerKontainerRunner
import io.microkt.kontainers.docker.runner.isOsLinux
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer
import mu.KotlinLogging

/**
 * Provides a Docker based [PlatformKontainer] implementation.
 *
 * @author Scott Rossillo
 */
class DockerKontainer(
    override val kontainerSpec: KontainerSpec,
    override val id: String,
    private val dockerKontainerRunner: DockerKontainerRunner
) : PlatformKontainer {

    private val log = KotlinLogging.logger {}

    private var containerInfo: InspectContainerResponse? = null

    private fun assertContainerInfo() {
        if (containerInfo == null) {
            throw IllegalStateException("Container not yet running")
        }
    }

    override suspend fun start(timeout: Long) {
        dockerKontainerRunner.start(id)
        containerInfo = dockerKontainerRunner.inspect(id)
    }

    override suspend fun remove() {
        dockerKontainerRunner.delete(id)
    }

    override fun getPort(): Int? =
        this.getPort(kontainerSpec.ports.first().port)

    override fun getPort(containerPort: Int): Int? =
        containerInfo!!.networkSettings.ports.bindings.entries
            .filter { it.value != null }
            .firstOrNull() { (k, _) -> k.port == containerPort }
            ?.value?.first()?.hostPortSpec?.toInt()

    override fun getAddress(): String =
        assertContainerInfo().run {
            return if (isOsLinux()) containerInfo!!.networkSettings.gateway else "localhost"
        }

    override fun getDirectAddress(): String? =
        assertContainerInfo().run {
            return containerInfo!!.networkSettings.networks.values.filterNotNull().first().ipAddress
        }
}
