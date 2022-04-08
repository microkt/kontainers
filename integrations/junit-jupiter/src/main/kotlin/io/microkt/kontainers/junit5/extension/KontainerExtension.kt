package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.spec.KontainerSpecOverride
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
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

/**
 * Provides a [JUnit Jupiter extension](https://junit.org/junit5/docs/current/user-guide/#extensions)
 * to simplify using [Kontainer]s in JUnit 5 test suites.
 *
 * @author Scott Rossillo
 *
 * @sample io.microkt.kontainers.junit5.KontainerExtensionTest
 * @see io.microkt.kontainers.junit5.annotation.Kontainers
 * @see io.microkt.kontainers.context.spec.KontainerSpecOverride
 */
open class KontainerExtension : AfterAllCallback, ParameterResolver, AbstractKontainerExtension() {
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

    final override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean =
        when {
            parameterContext!!.isKontainerType() -> supportsKontainerType(
                kontainerKClass = kontainerKClass(parameterContext.kotlinType()),
                parameterContext = parameterContext,
                extensionContext = extensionContext!!
            )
            else -> false
        }

    final override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Kontainer =
        when {
            kontainers.containsKey(parameterContext!!.index) -> kontainers[parameterContext.index]!!
            else -> resolveKontainer(parameterContext)
        }

    private fun resolveKontainer(parameterContext: ParameterContext): Kontainer {
        val factory = resolveKontainerFactory(kontainerKClass(parameterContext.kotlinType()), parameterContext)
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

    open fun resolveKontainerFactory(
        kontainerKClass: KClass<Kontainer>,
        parameterContext: ParameterContext
    ): KontainerFactory<out Kontainer> =
        findFactory(kontainerKClass)!!
}
