package io.microkt.kontainers.domain

import io.microkt.kontainers.domain.readiness.SocketReadinessProbe

open class GenericTcpKontainer(
    override val kontainerSpec: KontainerSpec,
    parent: Kontainer
) : GenericKontainer(kontainerSpec, parent) {
    override fun waitForReady(timeout: Long) {
        SocketReadinessProbe(
            host = getAddress()!!,
            port = getPort()!!,
            initialDelay = 1500
        ).waitUntilReady(timeout)
    }
}
