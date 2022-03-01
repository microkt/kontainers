package io.microkt.kontainers.redis

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class RedisKontainer(
    override val kontainerSpec: KontainerSpec,
    parent: Kontainer
) : GenericTcpKontainer(kontainerSpec, parent) {
    companion object Defaults {
        const val PORT = 6379
        const val VERSION = "6.2-alpine"
    }
}
