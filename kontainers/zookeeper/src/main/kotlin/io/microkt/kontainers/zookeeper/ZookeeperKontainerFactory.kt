package io.microkt.kontainers.zookeeper

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a [KontainerFactory] for [ZookeeperKontainer]s.
 *
 * @author Scott Rossillo
 */
class ZookeeperKontainerFactory : AbstractKontainerFactory<ZookeeperKontainer>(), KontainerFactory<ZookeeperKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = zookeeperKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): ZookeeperKontainer =
        ZookeeperKontainer(
            kontainerSpec = kontainerSpec,
            parent = runner.createSync(kontainerSpec)
        )

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == ZookeeperKontainer::class
}
