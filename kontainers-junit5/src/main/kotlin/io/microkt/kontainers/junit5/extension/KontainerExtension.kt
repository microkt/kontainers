package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import kotlin.reflect.KClass

open class KontainerExtension : AfterAllCallback, ParameterResolver {
    private val log = KotlinLogging.logger { }
    private val kontainers: MutableMap<Int, Kontainer> = mutableMapOf()

    override fun afterAll(context: ExtensionContext?) {
        runBlocking(Dispatchers.IO) {
            kontainers.values.map { async { it.remove() } }.awaitAll()
        }
    }

    final override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean =
        when {
            parameterContext!!.isKontainerType() -> supportsKontainerType(
                kontainerKClass = kontainerKClass(parameterContext.kotlinType()),
                parameterContext = parameterContext,
                extensionContext = extensionContext!!
            )
            else -> false
        }

    open fun supportsKontainerType(
        kontainerKClass: KClass<Kontainer>,
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean = true

    final override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Kontainer =
        when {
            kontainers.containsKey(parameterContext!!.index) -> kontainers[parameterContext.index]!!
            else -> resolveKontainer(parameterContext)
        }

    private fun customize(kontainerSpec: KontainerSpec, override: KontainerSpecOverride): KontainerSpec {
        log.info { "applying container spec overrides: $override" }
        return KontainerSpecCustomizer(kontainerSpec).customize(override)
    }

    private fun resolveKontainer(parameterContext: ParameterContext): Kontainer {
        val factory = resolveKontainerFactory(kontainerKClass(parameterContext.kotlinType()), parameterContext)
        val ann = parameterContext.findAnnotation(KontainerSpecOverride::class.java)
        val spec = if (ann.isPresent) customize(factory.kontainerSpec, ann.get()) else factory.kontainerSpec

        val k = factory.createKontainer(spec)
        kontainers[parameterContext.index] = k

        runBlocking(Dispatchers.IO) {
            k.start(timeout = 60_000) // FIXME: hard coded timeout
        }

        return k
    }

    open fun resolveKontainerFactory(
        kontainerKClass: KClass<Kontainer>,
        parameterContext: ParameterContext
    ): KontainerFactory<out Kontainer> =
        findFactory(kontainerKClass)!!
}
