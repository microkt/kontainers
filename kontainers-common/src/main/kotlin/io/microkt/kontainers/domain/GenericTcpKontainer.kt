package io.microkt.kontainers.domain

import io.microkt.kontainers.domain.readiness.SocketReadinessProbe

/**
 * Provides a base implementation's [GenericKontainer.waitForReady] based on
 * TCP socket connectivity to the [Kontainer]'s default TCP port.
 *
 * @author Scott Rossillo
 */
open class GenericTcpKontainer(
    override val kontainerSpec: KontainerSpec,
    parent: Kontainer
) : GenericKontainer(kontainerSpec, parent) {

    /**
     * Waits until this [Kontainer]'s default TCP port is up and accepting requests
     * or the given [timeout] is reached.
     */
    override fun waitForReady(timeout: Long) {
        SocketReadinessProbe(
            host = getAddress()!!,
            port = getPort()!!,
            initialDelay = 1500
        ).waitUntilReady(timeout)
    }
}
