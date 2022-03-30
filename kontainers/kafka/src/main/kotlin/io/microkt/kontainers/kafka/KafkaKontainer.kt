package io.microkt.kontainers.kafka

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer

class KafkaKontainer(
    kontainerSpec: KontainerSpec = kafkaKontainerSpec,
    delegate: PlatformKontainer,
    private val dependencies: Set<Kontainer>
) : GenericTcpKontainer(kontainerSpec, delegate) {
    override suspend fun remove() {
        dependencies.map { it.remove() }
        super.remove()
    }
}
