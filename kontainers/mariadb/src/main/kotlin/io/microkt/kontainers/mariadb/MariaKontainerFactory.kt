package io.microkt.kontainers.mariadb

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.createKontainer
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a [KontainerFactory] for [MariaDB Kontainer][MariaKontainer]s.
 *
 * @author Scott Rossillo
 */
class MariaKontainerFactory : AbstractKontainerFactory<MariaKontainer>(), KontainerFactory<MariaKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = mariaKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): MariaKontainer =
        createKontainer(kontainerSpec, runner.createSync(kontainerSpec))

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == MariaKontainer::class
}
