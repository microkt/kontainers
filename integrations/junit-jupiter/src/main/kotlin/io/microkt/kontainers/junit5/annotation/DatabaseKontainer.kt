package io.microkt.kontainers.junit5.annotation

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.junit5.extension.DatabaseKontainerExtension
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KClass

/**
 * Annotation indicating the JUnit test suite should be [extended with][ExtendWith]
 * the [DatabaseKontainerExtension] JUnit Jupiter Extension.
 *
 * @author Scott Rossillo
 * @see DatabaseKontainerExtension
 * @sample io.microkt.kontainers.spring.KontainersDemoApplicationTests
 */
@Target(AnnotationTarget.CLASS)
@ExtendWith(DatabaseKontainerExtension::class)
annotation class DatabaseKontainer(
    /**
     * The [JdbcKontainer] [KClass] to make available for integration testing.
     */
    val value: KClass<out JdbcKontainer>,

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
