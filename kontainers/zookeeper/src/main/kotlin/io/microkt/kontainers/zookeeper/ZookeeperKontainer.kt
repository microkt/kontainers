package io.microkt.kontainers.zookeeper

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class ZookeeperKontainer(
    override val kontainerSpec: KontainerSpec,
    parent: Kontainer
) : GenericTcpKontainer(kontainerSpec, parent) {
    companion object Defaults {
        const val ZOOKEEPER_IMAGE = "zookeeper"
        const val ZOOKEEPER_VERSION = "3.6.3"
        const val PORT: Int = 2181
    }
}
