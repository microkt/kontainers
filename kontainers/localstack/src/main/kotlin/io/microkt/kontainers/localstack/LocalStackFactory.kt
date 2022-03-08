package io.microkt.kontainers.localstack

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * [KontainerFactory] for [LocalStackKontainer]s.
 *
 * @author Scott Rossillo
 * @see KontainerFactory
 */
class LocalStackFactory : AbstractKontainerFactory<LocalStackKontainer>(), KontainerFactory<LocalStackKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = localStackKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): LocalStackKontainer {
        return LocalStackKontainer(
            kontainerSpec = kontainerSpec,
            parentHandle = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<Kontainer>): Boolean =
        kontainerKClass == LocalStackKontainer::class
}
