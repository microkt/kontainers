package io.microkt.kontainers.redis

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer

/**
 * Provides a Redis [Kontainer].
 *
 * @author Scott Rossillo
 */
class RedisKontainer(
    override val kontainerSpec: KontainerSpec,
    worker: PlatformKontainer
) : GenericTcpKontainer(kontainerSpec, worker)
