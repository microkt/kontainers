package io.microkt.kontainers.mongo

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

/**
 * Provides a MongoDB [Kontainer].
 */
class MongoKontainer(
    override val kontainerSpec: KontainerSpec,
    parentHandle: Kontainer
) : GenericTcpKontainer(kontainerSpec, parentHandle) {

    object Default {
        const val IMAGE = "mongo"
        const val PORT = 27017
        const val USERNAME = "test"
        const val PASSWORD = "test"
        const val VERSION = "5.0.6"
    }

    object Env {
        const val MONGO_INITDB_ROOT_USERNAME = "MONGO_INITDB_ROOT_USERNAME"
        const val MONGO_INITDB_ROOT_PASSWORD = "MONGO_INITDB_ROOT_PASSWORD"
    }
}
