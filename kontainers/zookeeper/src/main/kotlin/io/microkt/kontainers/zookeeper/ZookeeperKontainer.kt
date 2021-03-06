package io.microkt.kontainers.zookeeper

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer

/**
* Provides an [Apache Zookeeper](https://zookeeper.apache.org/) [Kontainer].
 *
 * @author Scott Rossillo
*/
class ZookeeperKontainer(
    override val kontainerSpec: KontainerSpec,
    delegate: PlatformKontainer
) : GenericTcpKontainer(kontainerSpec, delegate)
