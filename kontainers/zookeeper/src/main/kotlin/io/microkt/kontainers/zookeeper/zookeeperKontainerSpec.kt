package io.microkt.kontainers.zookeeper

import io.microkt.kontainers.dsl.kontainerSpec

val zookeeperKontainerSpec = kontainerSpec {
    name = "zookeeper"
    image = "${ZookeeperKontainer.ZOOKEEPER_IMAGE}:${ZookeeperKontainer.ZOOKEEPER_VERSION}"
    environment {
        set("ALLOW_ANONYMOUS_LOGIN" to "yes")
    }
    ports {
        expose tcp ZookeeperKontainer.PORT
    }
}
