package io.microkt.kontainers.domain

/**
 * Represents a [port] number and [protocol] to be exposed by a [Kontainer].
 *
 * @author Scott Rossillo
 */
open class KontainerPort(
    /**
     * The Kontainer port number.
     */
    val port: Int,

    /**
     * The Kontainer protocol.
     */
    val protocol: Protocol
) {
    /**
     * Enumeration of supported network protocols than can be used to establish
     * basic [Kontainer] availability. [Kontainer]s may use other protocols built on
     * top of these protocols; for example, HTTP, HTTPS, and GRPC.
     */
    enum class Protocol { TCP, UDP }

    companion object {
        /**
         * Creates a TCP [KontainerPort] with the given [port] number.
         */
        fun tcp(port: Int): KontainerPort = KontainerPort(port, Protocol.TCP)

        /**
         * Creates a UDP [KontainerPort] with the given [port] number.
         */
        fun udp(port: Int): KontainerPort = KontainerPort(port, Protocol.UDP)
    }
}
