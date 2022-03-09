package io.microkt.kontainers.mongo

import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.mongo.MongoKontainer.Env.MONGO_INITDB_ROOT_PASSWORD
import io.microkt.kontainers.mongo.MongoKontainer.Env.MONGO_INITDB_ROOT_USERNAME

/**
 * Default MongoDB [kontainerSpec].
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.mongo.mongoKontainerSpec]
 */
val mongoKontainerSpec = kontainerSpec {
    name = "mongo"
    image = "mongo:5.0.6"
    environment {
        set(MONGO_INITDB_ROOT_USERNAME to "test")
        set(MONGO_INITDB_ROOT_PASSWORD to "test")
    }
    ports {
        expose tcp 27017
    }
}
