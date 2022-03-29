package io.microkt.kontainers.mongo

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a [MongoKontainer][KontainerFactory].
 *
 * @author Scott Rossillo
 */
class MongoKontainerFactory : AbstractKontainerFactory<MongoKontainer>(), KontainerFactory<MongoKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = mongoKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): MongoKontainer {
        return MongoKontainer(
            kontainerSpec = kontainerSpec,
            parent = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == MongoKontainer::class
}
