package io.microkt.kontainers.junit5.annotation

/**
 * Provides an annotation to override a [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec]'s
 * image and environment on a JUnit 5 test.
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.junit5.KontainerExtensionTest
 */
annotation class KontainerSpecOverride(
    val image: String = "",
    val environment: Array<String> = []
)
