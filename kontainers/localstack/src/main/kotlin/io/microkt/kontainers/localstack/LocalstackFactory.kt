package io.microkt.kontainers.localstack

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

class LocalstackFactory : AbstractKontainerFactory<LocalstackKontainer>(), KontainerFactory<LocalstackKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = localstackKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): LocalstackKontainer {
        return LocalstackKontainer(
            kontainerSpec = kontainerSpec,
            parentHandle = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<Kontainer>): Boolean =
        kontainerKClass == LocalstackKontainer::class
}
