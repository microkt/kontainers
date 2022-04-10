package io.microkt.kontainers.context.spec

import kotlin.reflect.KClass

/**
 * Provides an annotation to override portions of a [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec].
 * Mainly useful with JUnit Jupiter tests.
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.junit5.KontainerExtensionTest
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class KontainerSpecOverride(
    /**
     * TODO: docs
     */
    val value: KClass<out KontainerSpecProvider>
)
