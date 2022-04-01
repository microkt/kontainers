package io.microkt.kontainers.kafka

import io.microkt.kontainers.config.KontainerPropertyDelegate
import io.microkt.kontainers.domain.BoundKontainerPort
import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner
import io.microkt.kontainers.runner.KontainerRunnerFactory
import io.microkt.kontainers.zookeeper.zookeeperKontainerSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket
import kotlin.reflect.KClass

class KafkaKontainerFactory : KontainerFactory<KafkaKontainer> {
    private val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }
    override val kontainerSpec: KontainerSpec
        get() = kafkaKontainerSpec

    private val dependsOn: Set<KontainerSpec> = setOf(
        zookeeperKontainerSpec
    )

    private fun createDependencies(): Set<Kontainer> =
        dependsOn.map { spec ->
            GenericTcpKontainer(
                kontainerSpec = spec,
                delegate = runner.createSync(spec)
            )
        }.toSet()

    private fun randomPort(): Int =
        ServerSocket(0).use { it.localPort }

    private fun remapKontainerSpec(kontainerSpec: KontainerSpec, zookeeperAddress: String): KontainerSpec {
        val bindPort = randomPort()
        return kontainerSpec.copy(
            environment = mutableMapOf("KAFKA_CFG_ZOOKEEPER_CONNECT" to zookeeperAddress)
                .also { env ->
                    kontainerSpec.environment.forEach { (k, v) -> env.putIfAbsent(k, v) }
                    if (KontainerRunnerFactory.determineBackend() == KontainerRunnerFactory.Backend.DOCKER) {
                        env["KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP"] = "CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT"
                        env["KAFKA_CFG_LISTENERS"] = "CLIENT://:9092,EXTERNAL://:9093"
                        env["KAFKA_CFG_ADVERTISED_LISTENERS"] = "CLIENT://localhost:9092,EXTERNAL://localhost:$bindPort"
                        env["KAFKA_CFG_INTER_BROKER_LISTENER_NAME"] = "CLIENT"
                    } else {
                        env["KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP"] = "CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT"
                        env["KAFKA_CFG_LISTENERS"] = "CLIENT://:9092,EXTERNAL://:9093"
                        env["KAFKA_CFG_ADVERTISED_LISTENERS"] = "CLIENT://localhost:9092,EXTERNAL://:9093"
                        env["KAFKA_CFG_INTER_BROKER_LISTENER_NAME"] = "CLIENT"
                    }
                },
            ports = kontainerSpec.ports.map { kontainerPort ->
                when (kontainerPort.port) {
                    9093 -> BoundKontainerPort.of(kontainerPort, bindPort)
                    else -> kontainerPort
                }
            }
        )
    }

    override fun createKontainer(kontainerSpec: KontainerSpec): KafkaKontainer = runBlocking(Dispatchers.IO) {
        val dependencies: Set<Kontainer> = createDependencies()
        dependencies.map { async { it.start(30_000) } }.awaitAll()

        // TODO: allow specs to define dependencies
        val zkKontainer = dependencies.first()
        val zkAddress = "${zkKontainer.getDirectAddress()}:${zkKontainer.kontainerSpec.ports.first().port}"
        val customKafkaSpec = remapKontainerSpec(kontainerSpec, zkAddress)

        return@runBlocking KafkaKontainer(
            kontainerSpec = customKafkaSpec,
            delegate = runner.createSync(customKafkaSpec),
            dependencies = dependencies
        )
    }

    override fun createKontainer(): KafkaKontainer = createKontainer(kontainerSpec)

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == KafkaKontainer::class
}
