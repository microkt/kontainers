package io.microkt.kontainers.redis

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

/**
 * Provides a Redis [Kontainer].
 *
 * @author Scott Rossillo
 */
class RedisKontainer(
    override val kontainerSpec: KontainerSpec,
    parent: Kontainer
) : GenericTcpKontainer(kontainerSpec, parent) {
    /**
     * Redis Kontainer defaults.
     */
    companion object Defaults {
        /**
         * The default Redis image `redis`.
         */
        const val IMAGE = "redis"

        /**
         * The default Redis port.
         */
        const val PORT = 6379

        /**
         * The default Redis version `6.2-alpine`.
         */
        const val VERSION = "6.2-alpine"
    }
}
