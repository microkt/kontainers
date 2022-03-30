package io.microkt.kontainers.kafka

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec

val kafkaKontainerSpec = kontainerSpec {
    name = "kafka"
    image = "bitnami/kafka:2.8.1"
    environment {
        set("KAFKA_BROKER_ID" to "1")
        set("KAFKA_CFG_LISTENERS" to "PLAINTEXT://:9092")
        set("ALLOW_PLAINTEXT_LISTENER" to "yes")
    }
    ports {
        expose tcp 9092
    }
    resources {
        limit memory 512.MB
    }
}
