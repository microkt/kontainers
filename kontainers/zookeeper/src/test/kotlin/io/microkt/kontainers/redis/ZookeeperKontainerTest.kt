package io.microkt.kontainers.redis

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readUTF8Line
import io.microkt.kontainers.junit5.annotation.Kontainers
import io.microkt.kontainers.zookeeper.ZookeeperKontainer
import io.microkt.kontainers.zookeeper.zookeeperKontainerSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import java.net.InetSocketAddress
import java.nio.ByteBuffer

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class ZookeeperKontainerTest(
    private val zookeeperKontainer: ZookeeperKontainer
) {
    @Test
    fun testAvailable() = runBlocking {
        val socket = aSocket(ActorSelectorManager(Dispatchers.IO))
            .tcp()
            .connect(InetSocketAddress(zookeeperKontainer.getAddress(), zookeeperKontainer.getPort()!!))
        val input = socket.openReadChannel()
        val output = socket.openWriteChannel(autoFlush = true)

        output.apply {
            val command = ByteBuffer.wrap("srvr\r\n".toByteArray())
            writeFully(command)
        }

        assertTrue(input.readUTF8Line()!!.startsWith("Zookeeper"))
    }

    @Test
    fun testValidPort() {
        Assertions.assertNotNull(zookeeperKontainer.getPort(zookeeperKontainerSpec.ports.first().port))
    }
}
