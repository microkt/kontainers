package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.Kontainer
import mu.KotlinLogging
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
open class KontainerExtension : AbstractParameterResolverKontainerExtension() {
    private val log = KotlinLogging.logger { }

    override fun kontainerStarted(
        kontainer: Kontainer,
        customPropertySuppliers: List<KClass<out PropertySupplier>>,
        useDefaultPropertySuppliers: Boolean,
    ) {
        kontainer.populateEnvironment(customPropertySuppliers, useDefaultPropertySuppliers)
    }
}
