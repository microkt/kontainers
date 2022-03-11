package io.microkt.kontainers.mongo

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

/**
 * Provides a MongoDB [Kontainer].
 *
 * @author Scott Rossillo
 * @constructor Creates a new [MongoKontainer] with the given [kontainerSpec].
 * @sample io.microkt.kontainers.mongo.MongoKontainerTest
 */
class MongoKontainer(
    override val kontainerSpec: KontainerSpec,
    parent: Kontainer
) : GenericTcpKontainer(kontainerSpec, parent) {
    /**
     * MongoDB environment variables.
     */
    object Env {
        const val MONGO_INITDB_ROOT_USERNAME = "MONGO_INITDB_ROOT_USERNAME"
        const val MONGO_INITDB_ROOT_PASSWORD = "MONGO_INITDB_ROOT_PASSWORD"
    }
}
