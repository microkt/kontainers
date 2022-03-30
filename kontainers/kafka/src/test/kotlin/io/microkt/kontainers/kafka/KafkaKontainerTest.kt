package io.microkt.kontainers.kafka

import io.microkt.kontainers.junit5.annotation.Kontainers
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.KafkaAdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import java.util.Optional
import java.util.concurrent.TimeUnit

// @Disabled("unstable")
@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class KafkaKontainerTest(private val kafkaKontainer: KafkaKontainer) {

    private val adminClient = KafkaAdminClient.create(
        mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "${kafkaKontainer.getAddress()}:${kafkaKontainer.getPort()}"
        )
    )

    @Test
    fun testListTopics() {

        val createTopicsResult = adminClient.createTopics(setOf(NewTopic(TOPIC_NAME, Optional.empty(), Optional.empty())))

        println(createTopicsResult.values().keys)
        assertTrue(createTopicsResult.values().keys.contains(TOPIC_NAME))

        val listTopicsResult = adminClient.listTopics()
        val topics = listTopicsResult.names().get(10, TimeUnit.SECONDS)

        assertTrue(topics.contains(TOPIC_NAME))
    }

    @Test
    fun testValidPort() {
        assertNotNull(kafkaKontainer.getPort(kafkaKontainerSpec.ports.first().port))
    }

    companion object {
        private const val TOPIC_NAME = "foo-topic"
    }
}
