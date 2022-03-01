package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import org.junit.jupiter.api.extension.ParameterContext
import java.util.ServiceLoader
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private val loader = lazy { ServiceLoader.load(KontainerFactory::class.java) }

internal fun findFactory(kontainerKClass: KClass<Kontainer>): KontainerFactory<out Kontainer>? {
    val serviceLoader = loader.value

    if (serviceLoader.toCollection(mutableListOf()).isEmpty()) {
        throw IllegalStateException("service loader contains no Kontainer factories")
    }

    return serviceLoader.firstOrNull { it.supports(kontainerKClass) }
}

@Suppress("UNCHECKED_CAST")
internal fun kontainerKClass(kClass: KClass<out Any>): KClass<Kontainer> = kClass as KClass<Kontainer>

internal fun ParameterContext.kotlinType() = this.parameter!!.type!!.kotlin
internal fun ParameterContext.isKontainerType() = this.kotlinType().isSubclassOf(Kontainer::class)
