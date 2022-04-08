package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.spec.KontainerSpecCustomizer
import io.microkt.kontainers.context.spec.KontainerSpecOverride
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.R2dbcKontainer
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
 * @sample io.microkt.kontainers.junit5.KontainerExtensionTest
 * @see io.microkt.kontainers.junit5.annotation.Kontainers
 * @see io.microkt.kontainers.junit5.annotation.KontainerSpecOverride
 */
open class KontainerExtension : AfterAllCallback, ParameterResolver {
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

    /**
     * Applies @[KontainerSpecOverride]s to the [Kontainer]'s default [kontainerSpec].
     */
    private fun customize(kontainerSpec: KontainerSpec, override: KontainerSpecOverride): KontainerSpec {
        log.info { "applying container spec overrides: $override" }
        return KontainerSpecCustomizer(kontainerSpec).customize(override)
    }

    open fun kontainerStarted(kontainer: Kontainer) {
        if (kontainer is JdbcKontainer) {
            log.info { "Export Spring DataSource properties" }
            System.setProperty("spring.datasource.url", kontainer.createJdbcUrl())
            System.setProperty("spring.datasource.username", kontainer.getUsername())
            System.setProperty("spring.datasource.password", kontainer.getPassword())

            log.info { "Export Spring Flyway DataSource properties" }
            System.setProperty("spring.flyway.url", kontainer.createJdbcUrl())
            System.setProperty("spring.flyway.user", kontainer.getUsername())
            System.setProperty("spring.flyway.password", kontainer.getPassword())
        }

        if (kontainer is R2dbcKontainer) {
            log.info { "Export Spring R2DBC properties" }
            System.setProperty("spring.r2dbc.url", kontainer.createR2dbcUrl())
            System.setProperty("spring.r2dbc.username", kontainer.getUsername())
            System.setProperty("spring.r2dbc.password", kontainer.getPassword())
        }
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

        kontainerStarted(k)

        return k
    }

    open fun resolveKontainerFactory(
        kontainerKClass: KClass<Kontainer>,
        parameterContext: ParameterContext
    ): KontainerFactory<out Kontainer> =
        findFactory(kontainerKClass)!!
}
