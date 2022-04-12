package io.microkt.kontainers.junit5.annotation

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.junit5.extension.GenericKontainerExtension
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KClass

/**
 * Annotation indicating the JUnit test suite should be [extended with][ExtendWith]
 * the [JUnit Jupiter extension](https://junit.org/junit5/docs/current/user-guide/#extensions)
 * [GenericKontainerExtension].
 *
 * @author Scott Rossillo
 */
@Target(AnnotationTarget.CLASS)
@ExtendWith(GenericKontainerExtension::class)
annotation class Kontainer(
    /**
     * The [Kontainer] [KClass] to make available for integration testing.
     */
    val value: KClass<out io.microkt.kontainers.domain.Kontainer>,

    /**
     * Custom [PropertySupplier]s to configure the integration test environment.
     */
    val propertySuppliers: Array<KClass<out PropertySupplier>> = [],

    /**
     * True to register properties from any [PropertySupplier]s on the classpath;
     * false otherwise.
     */
    val useDefaultPropertySuppliers: Boolean = true
)
