package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.context.spec.KontainerSpecCustomizer
import io.microkt.kontainers.context.spec.KontainerSpecOverride
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
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

abstract class AbstractParameterResolverKontainerExtension : AfterAllCallback, ParameterResolver {
    private val log = KotlinLogging.logger { }
    private val kontainers: MutableMap<Int, Kontainer> = mutableMapOf()

    /**
     * Removes all [Kontainer]s started by this extension.
     */
    override fun afterAll(context: ExtensionContext?) {
        runBlocking(Dispatchers.IO) {
            kontainers.values.map { async { it.remove() } }.awaitAll()
        }
    }

    open fun supportsKontainerType(
        kontainerKClass: KClass<Kontainer>,
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean = true

    // TODO: why do I need to check the parma
    final override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean =
        when {
            parameterContext.isKontainerType() -> supportsKontainerType(
                kontainerKClass = kontainerKClass(parameterContext.kotlinType()),
                parameterContext = parameterContext,
                extensionContext = extensionContext!!
            )
            else -> false
        }

    final override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Kontainer =
        when {
            kontainers.containsKey(parameterContext.index) -> kontainers[parameterContext.index]!!
            else -> resolveKontainer(parameterContext)
        }

    /**
     * Applies @[KontainerSpecOverride]s to the [Kontainer]'s default [kontainerSpec].
     */
    fun customize(kontainerSpec: KontainerSpec, override: KontainerSpecOverride): KontainerSpec {
        log.info { "applying container spec overrides: $override" }
        return KontainerSpecCustomizer(kontainerSpec).customize(override)
    }

    abstract fun kontainerStarted(
        kontainer: Kontainer,
        customPropertySuppliers: List<KClass<out PropertySupplier>> = listOf(),
        useDefaultPropertySuppliers: Boolean = true
    )

    private fun resolveKontainer(parameterContext: ParameterContext): Kontainer {
        val factory = resolveKontainerFactory(kontainerKClass(parameterContext.kotlinType()))
        val ann = parameterContext.findAnnotation(KontainerSpecOverride::class.java)
        val spec = if (ann.isPresent) customize(factory.kontainerSpec, ann.get()) else factory.kontainerSpec

        log.info { "Creating Kontainer from spec: $spec" }

        val k = factory.createKontainer(spec)
        kontainers[parameterContext.index] = k

        runBlocking(Dispatchers.IO) {
            k.start(timeout = 60_000) // FIXME: hard coded timeout
        }

        kontainerStarted(k)

        return k
    }

    private fun resolveKontainerFactory(
        kontainerKClass: KClass<Kontainer>
    ): KontainerFactory<out Kontainer> =
        findFactory(kontainerKClass)!!
}
