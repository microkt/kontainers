package io.microkt.kontainers.domain

class BoundKontainerPort(port: Int, protocol: Protocol, val bindPort: Int) : KontainerPort(
    port = port,
    protocol = protocol
) {
    companion object {
        fun of(kontainerPort: KontainerPort, bindPort: Int): BoundKontainerPort =
            BoundKontainerPort(kontainerPort.port, kontainerPort.protocol, bindPort)
    }
}
