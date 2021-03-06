package io.microkt.kontainers.mongo

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer

/**
 * Provides a MongoDB [Kontainer].
 *
 * @author Scott Rossillo
 * @constructor Creates a new [MongoKontainer] with the given [kontainerSpec].
 * @sample io.microkt.kontainers.mongo.MongoKontainerTest
 */
class MongoKontainer(
    override val kontainerSpec: KontainerSpec,
    delegate: PlatformKontainer
) : GenericTcpKontainer(kontainerSpec, delegate) {
    /**
     * MongoDB environment variables.
     */
    object Env {
        const val MONGO_INITDB_ROOT_USERNAME = "MONGO_INITDB_ROOT_USERNAME"
        const val MONGO_INITDB_ROOT_PASSWORD = "MONGO_INITDB_ROOT_PASSWORD"
    }
}
