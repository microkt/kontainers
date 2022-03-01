package io.microkt.kontainers.kafka

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class KafkaKontainer(
    kontainerSpec: KontainerSpec = kafkaKontainerSpec,
    parent: Kontainer,
    private val dependencies: Set<Kontainer>
) : GenericTcpKontainer(kontainerSpec, parent) {
    override suspend fun remove() {
        dependencies.map { it.remove() }
        super.remove()
    }
}
