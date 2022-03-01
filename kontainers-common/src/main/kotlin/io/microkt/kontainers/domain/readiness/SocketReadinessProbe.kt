package io.microkt.kontainers.domain.readiness

import io.microkt.kontainers.runner.retryOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import java.io.IOException
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.time.Instant

class SocketReadinessProbe(
    private val host: String,
    private val port: Int,
    private val initialDelay: Long = 1_000
) : ReadinessProbe {
    private val log = KotlinLogging.logger {}
    private var retries: Int = 0

    internal fun getRetries(): Int = retries

    private fun socket(timeout: Int = 500): Socket? =
        try {
            val sock = Socket()
            sock.connect(InetSocketAddress(host, port), timeout)
            sock
        } catch (e: SocketTimeoutException) {
            null
        } catch (e: ConnectException) {
            null
        } catch (e: IOException) {
            null
        }

    override fun waitUntilReady(timeout: Long) = runBlocking(Dispatchers.IO) {
        val start = Instant.now()
        retryOperation<Unit>(retries = 100, initialDelay = initialDelay, maxDelay = 1_000) {
            val sock = socket()
            if (sock != null) {
                sock.use {
                    log.info { "Socket ready at $host:$port" }
                }
            } else if (Instant.now().isAfter(start.plusMillis(timeout))) {
                throw IllegalStateException("Timed out waiting for connection to $host:$port")
            } else {
                retries++
                operationFailed()
            }
        }
    }
}
