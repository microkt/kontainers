package io.microkt.kontainers.zookeeper

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner
import io.microkt.kontainers.runner.KontainerRunnerFactory
import kotlin.reflect.KClass

class ZookeeperKontainerFactory : KontainerFactory<ZookeeperKontainer> {
    private val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }
    override val kontainerSpec: KontainerSpec
        get() = zookeeperKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): ZookeeperKontainer =
        ZookeeperKontainer(
            kontainerSpec = kontainerSpec,
            parent = runner.createSync(kontainerSpec)
        )

    override fun createKontainer(): ZookeeperKontainer = createKontainer(kontainerSpec)

    override fun supports(kontainerKClass: KClass<Kontainer>): Boolean =
        kontainerKClass == ZookeeperKontainer::class
}
