package io.microkt.kontainers.domain.readiness

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.ServerSocket

internal class SocketReadinessProbeTest {

    @Test
    fun waitUntilReadyImmediate() {
        val server = ServerSocket(0)
        val probe = SocketReadinessProbe(
            host = "localhost",
            port = server.localPort,
            initialDelay = 500
        )

        probe.waitUntilReady(1_000)
        server.close()

        assertEquals(0, probe.getRetries(), "Expected zero retries")
    }

    @Test
    fun waitUntilReadyDelay() {
        val port = ServerSocket(0).use { it.localPort }

        val probe = SocketReadinessProbe(
            host = "localhost",
            port = port,
            initialDelay = 500
        )

        var server: ServerSocket? = null

        runBlocking {
            val probeHandle = async { probe.waitUntilReady(5_000) }
            val serverHandle = async(Dispatchers.IO) {
                delay(1_000)
                server = ServerSocket(port)
            }
            awaitAll(probeHandle, serverHandle)
        }

        server?.close()

        assertEquals(1, probe.getRetries(), "Expected one retry")
    }

    @Test
    fun waitUntilReadyNever() {
        val port = ServerSocket(0).use { it.localPort }
        val probe = SocketReadinessProbe(
            host = "localhost",
            port = port,
            initialDelay = 100
        )

        val ex = assertThrows<IllegalStateException> {
            probe.waitUntilReady(2_000)
        }

        assertTrue(probe.getRetries() > 0, "Expected more than zero retries")
        assertNotNull(ex.message)
    }
}
