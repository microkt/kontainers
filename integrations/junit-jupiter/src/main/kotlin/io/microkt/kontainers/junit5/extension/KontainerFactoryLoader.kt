package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import java.util.ServiceLoader
import kotlin.reflect.KClass

private val loader = lazy { ServiceLoader.load(KontainerFactory::class.java) }

internal fun findFactory(kontainerKClass: KClass<out Kontainer>): KontainerFactory<out Kontainer>? {
    val serviceLoader = loader.value

    if (serviceLoader.toCollection(mutableListOf()).isEmpty()) {
        throw IllegalStateException("service loader contains no Kontainer factories")
    }

    return serviceLoader.firstOrNull { it.supports(kontainerKClass) }
}

@Suppress("UNCHECKED_CAST")
internal fun kontainerKClass(kClass: KClass<out Any>): KClass<Kontainer> = kClass as KClass<Kontainer>
