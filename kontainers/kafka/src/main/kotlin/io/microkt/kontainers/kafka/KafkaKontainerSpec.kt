package io.microkt.kontainers.kafka

import io.microkt.kontainers.domain.GB
import io.microkt.kontainers.dsl.kontainerSpec

val kafkaKontainerSpec = kontainerSpec() {
    name = "kafka"
    image = "bitnami/kafka:2.8.1"
    environment {
        // set("KAFKA_BROKER_ID" to "1")
        // set("KAFKA_CFG_LISTENERS" to "PLAINTEXT://:9092")
        set("KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP" to "CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT")
        set("KAFKA_CFG_LISTENERS" to "CLIENT://:9092,EXTERNAL://:9093")
        set("KAFKA_CFG_ADVERTISED_LISTENERS" to "CLIENT://localhost:9092,EXTERNAL://localhost:9093")
        set("ALLOW_PLAINTEXT_LISTENER" to "yes")
        set("KAFKA_CFG_INTER_BROKER_LISTENER_NAME" to "CLIENT")
    }
    ports {
        expose tcp 9092
        expose tcp 9093
    }
    resources {
        limit memory 1.5.GB
    }
}
