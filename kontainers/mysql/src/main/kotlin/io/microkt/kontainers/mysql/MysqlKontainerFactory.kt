package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

class MysqlKontainerFactory : AbstractKontainerFactory<MysqlKontainer>(), KontainerFactory<MysqlKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = mysqlKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): MysqlKontainer {
        return MysqlKontainer(
            kontainerSpec = kontainerSpec,
            parentHandle = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<Kontainer>): Boolean =
        kontainerKClass == MysqlKontainer::class
}
