package io.microkt.kontainers.junit5.annotation

import io.microkt.kontainers.junit5.extension.KontainerExtension
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Annotation indicating the JUnit test suite should be [extended with][ExtendWith]
 * the [JUnit Jupiter extension](https://junit.org/junit5/docs/current/user-guide/#extensions)
 * [KontainerExtension].
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.junit5.KontainerExtensionTest
 */
@Target(AnnotationTarget.CLASS)
@ExtendWith(KontainerExtension::class)
annotation class Kontainers
