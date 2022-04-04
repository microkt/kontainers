package io.microkt.kontainers.junit5.annotation

import io.microkt.kontainers.junit5.KontainerProvider
import kotlin.reflect.KClass

/**
 * Provides an annotation to override a [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec]'s
 * image and environment on a JUnit 5 test.
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.junit5.KontainerExtensionTest
 */
@MustBeDocumented
// @Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
// @Retention(AnnotationRetention.RUNTIME)
annotation class KontainerSpecOverride(
    /**
     * TODO: docs
     */
    val value: KClass<out KontainerProvider>
)
