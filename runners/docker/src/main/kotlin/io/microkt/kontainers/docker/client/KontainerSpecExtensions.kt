package io.microkt.kontainers.docker.client

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import io.microkt.kontainers.domain.KontainerPort
import io.microkt.kontainers.domain.KontainerSpec

internal fun KontainerPort.exposedPort(): ExposedPort =
    when (protocol) {
        KontainerPort.Protocol.TCP -> ExposedPort.tcp(port)
        KontainerPort.Protocol.UDP -> ExposedPort.udp(port)
    }

internal fun KontainerSpec.exposedPorts(): List<ExposedPort> = ports.map { it.exposedPort() }
internal fun KontainerSpec.portBindings(): List<PortBinding> = ports.map { port ->
    when (port.exposedPort().port) {
        9092 -> PortBinding(Ports.Binding.bindPort(9092), port.exposedPort())
        else -> PortBinding(Ports.Binding.empty(), port.exposedPort())
    }
}
