package io.microkt.kontainers.mongo

import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.mongo.MongoKontainer.Env.MONGO_INITDB_ROOT_PASSWORD
import io.microkt.kontainers.mongo.MongoKontainer.Env.MONGO_INITDB_ROOT_USERNAME

val mongoKontainerSpec = kontainerSpec {
    name = MongoKontainer.Default.IMAGE
    image = "${this.name}:${MongoKontainer.Default.VERSION}"
    environment {
        set(MONGO_INITDB_ROOT_USERNAME to MongoKontainer.Default.USERNAME)
        set(MONGO_INITDB_ROOT_PASSWORD to MongoKontainer.Default.PASSWORD)
    }
    ports {
        expose tcp MongoKontainer.Default.PORT
    }
}
