package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a [KontainerFactory] for [MysqlKontainer]s.
 *
 * @author Scott Rossillo
 */
class MysqlKontainerFactory : AbstractKontainerFactory<MysqlKontainer>(), KontainerFactory<MysqlKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = mysqlKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): MysqlKontainer {
        return MysqlKontainer(
            kontainerSpec = kontainerSpec,
            parentKontainer = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == MysqlKontainer::class
}
