package io.microkt.kontainers.kafka

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

    override fun createKontainer(kontainerSpec: KontainerSpec): KafkaKontainer = runBlocking(Dispatchers.IO) {
        val dependencies: Set<Kontainer> = createDependencies()
        dependencies.map { async { it.start(30_000) } }.awaitAll()

        // we know there's only one
        val zkKontainer = dependencies.first()
        val zkAddress = "${zkKontainer.getDirectAddress()}:${zkKontainer.kontainerSpec.ports.first().port}"

        val customKafkaSpec = kontainerSpec.copy(
            environment = mutableMapOf("KAFKA_CFG_ZOOKEEPER_CONNECT" to zkAddress)
                .also { env ->
                    env.putAll(kontainerSpec.environment)
                    if (KontainerRunnerFactory.determineBackend() == KontainerRunnerFactory.Backend.DOCKER) {
                        env["KAFKA_CFG_ADVERTISED_LISTENERS"] = "PLAINTEXT://127.0.0.1:9092"
                    }
                }
        )

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
