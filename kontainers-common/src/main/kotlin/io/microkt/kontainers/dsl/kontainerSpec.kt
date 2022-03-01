package io.microkt.kontainers.dsl

import io.microkt.kontainers.domain.KontainerPort
import io.microkt.kontainers.domain.KontainerSpec

fun kontainerSpec(block: KontainerSpecBuilder.() -> Unit): KontainerSpec =
    KontainerSpecBuilder().apply(block).build()

fun kontainerSpec(baseSpec: KontainerSpec, block: KontainerSpecBuilder.() -> Unit): KontainerSpec =
    KontainerSpecBuilder(baseSpec).apply(block).build()

@KontainerDsl
class KontainerEnvironmentBuilder {
    private val env: MutableMap<String, String> = mutableMapOf()

    infix fun set(kv: Pair<String, String>) {
        env[kv.first] = kv.second
    }

    fun build(): Map<String, String> = env
}

@KontainerDsl
class KontainerPortBuilder {
    val expose = this
    private val ports: MutableList<KontainerPort> = mutableListOf()

    infix fun tcp(port: Int) {
        ports.add(KontainerPort.tcp(port))
    }

    infix fun udp(port: Int) {
        ports.add(KontainerPort.udp(port))
    }

    fun build(): List<KontainerPort> = ports
}

@KontainerDsl
class KontainerSpecBuilder() {
    constructor(baseSpec: KontainerSpec) : this() {
        name = baseSpec.name
        image = baseSpec.image
        command = baseSpec.command
        environment.putAll(baseSpec.environment)
        ports.addAll(baseSpec.ports)
    }

    var name: String? = null
    var image: String? = null
    var command: List<String> = listOf()
    private val environment: MutableMap<String, String> = mutableMapOf()
    private val ports: MutableList<KontainerPort> = mutableListOf()

    fun ports(block: KontainerPortBuilder.() -> Unit) {
        val b = KontainerPortBuilder()
        b.run(block)
        ports.addAll(b.build())
    }

    fun environment(block: KontainerEnvironmentBuilder.() -> Unit) {
        val env = KontainerEnvironmentBuilder().apply(block).build()
        environment.putAll(env)
    }

    fun build(): KontainerSpec {
        if (ports.isEmpty()) {
            throw IllegalStateException("One or more port(s) must be configured")
        }
        return KontainerSpec(
            name = name!!,
            image = image!!,
            ports = ports,
            command = command,
            environment = environment,
        )
    }
}
