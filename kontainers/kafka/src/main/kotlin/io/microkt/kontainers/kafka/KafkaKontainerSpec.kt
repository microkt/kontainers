package io.microkt.kontainers.kafka

import io.microkt.kontainers.domain.GB
import io.microkt.kontainers.dsl.kontainerSpec

val kafkaKontainerSpec = kontainerSpec() {
    name = "kafka"
    image = "bitnami/kafka:2.8.1"
    environment {
        set("KAFKA_BROKER_ID" to "1")
        set("ALLOW_PLAINTEXT_LISTENER" to "yes")
    }
    ports {
        expose tcp 9092
        expose tcp 9093
    }
    resources {
        limit memory 1.5.GB
    }
}
