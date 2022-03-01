package io.microkt.kontainers.docker.domain

import com.github.dockerjava.api.command.InspectContainerResponse
import io.microkt.kontainers.docker.runner.DockerKontainerRunner
import io.microkt.kontainers.docker.runner.isOsLinux
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import mu.KotlinLogging

class DockerKontainer(
    override val kontainerSpec: KontainerSpec,
    override val id: String,
    private val dockerKontainerRunner: DockerKontainerRunner
) : Kontainer {

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

    override fun getPort(): Int {
        assertContainerInfo()
        val portBindings = containerInfo!!.networkSettings.ports.bindings
        // FIXME: this may cause an exception AND may not be the right port, add test
        val binding = portBindings.values.filterNotNull().first().filterNotNull().first()
        return binding.hostPortSpec.toInt()
    }

    override fun getPort(containerPort: Int): Int? {
        val portBindings = containerInfo!!.networkSettings.ports.bindings
        // FIXME: needs better code cov
        val portMap = portBindings.entries.filter { it.value != null }.associate { (k, v) -> (k.port to v.first().hostPortSpec.toInt()) }
        return portMap[containerPort]
    }

    override fun getAddress(): String {
        assertContainerInfo()
        return if (isOsLinux()) containerInfo!!.networkSettings.gateway else "localhost"
    }

    override fun getDirectAddress(): String? {
        assertContainerInfo()
        return containerInfo!!.networkSettings.networks.values.filterNotNull().first().ipAddress
    }
}
