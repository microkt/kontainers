package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.context.spec.KontainerSpecCustomizer
import io.microkt.kontainers.context.spec.KontainerSpecOverride
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import mu.KotlinLogging
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import java.util.ServiceLoader
import kotlin.reflect.KClass

abstract class AbstractKontainerExtension {
    private val log = KotlinLogging.logger { }
    private val propertySupplierLoader = lazy { ServiceLoader.load(PropertySupplier::class.java) }

    open fun supportsKontainerType(
        kontainerKClass: KClass<Kontainer>,
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean = true

    /**
     * Applies @[KontainerSpecOverride]s to the [Kontainer]'s default [kontainerSpec].
     */
    fun customize(kontainerSpec: KontainerSpec, override: KontainerSpecOverride): KontainerSpec {
        log.info { "applying container spec overrides: $override" }
        return KontainerSpecCustomizer(kontainerSpec).customize(override)
    }

    fun kontainerStarted(kontainer: Kontainer) {
        propertySupplierLoader.value.forEach { supplier ->
            supplier.supply(kontainer).forEach { (k, v) ->
                log.info { "setting sys prop $k=$v" }
                System.setProperty(k, v)
            }
        }
    }
}
