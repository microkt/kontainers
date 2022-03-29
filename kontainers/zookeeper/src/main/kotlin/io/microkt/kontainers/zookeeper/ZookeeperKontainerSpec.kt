package io.microkt.kontainers.zookeeper

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec

/**
 * Provides an [Apache Zookeeper](https://zookeeper.apache.org/)
 * [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec].
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.zookeeper.zookeeperKontainerSpec]
 */
val zookeeperKontainerSpec = kontainerSpec {
    name = "zookeeper"
    image = "zookeeper:3.6.3"
    environment {
        set("ALLOW_ANONYMOUS_LOGIN" to "yes")
    }
    ports {
        expose tcp 2181
    }
    resources {
        limit memory 256.MB
    }
}
