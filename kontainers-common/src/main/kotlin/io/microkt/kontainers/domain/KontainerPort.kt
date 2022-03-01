package io.microkt.kontainers.domain

data class KontainerPort(
    val port: Int,
    val protocol: Protocol
) {
    enum class Protocol { TCP, UDP }

    companion object {
        fun tcp(port: Int): KontainerPort = KontainerPort(port, Protocol.TCP)
        fun udp(port: Int): KontainerPort = KontainerPort(port, Protocol.UDP)
    }
}
