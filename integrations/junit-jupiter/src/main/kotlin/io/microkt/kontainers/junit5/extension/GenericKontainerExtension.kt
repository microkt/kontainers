package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.Kontainer
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.reflect.KClass

/**
 * Provides a [JUnit Jupiter extension](https://junit.org/junit5/docs/current/user-guide/#extensions)
 * capable of running a single Kontainer for integration testing.
 *
 * @author Scott Rossillo
 * @see io.microkt.kontainers.junit5.annotation.DatabaseKontainer
 */
class GenericKontainerExtension : AbstractBeforeAfterKontainerExtension() {

    override fun kontainerKClass(context: ExtensionContext): KClass<out Kontainer> =
        findAnnotation(context).value

    override fun propertySuppliers(context: ExtensionContext): Array<KClass<out PropertySupplier>> =
        findAnnotation(context).propertySuppliers

    override fun useDefaultPropertySuppliers(context: ExtensionContext): Boolean =
        findAnnotation(context).useDefaultPropertySuppliers

    override suspend fun isReady(kontainer: Kontainer): Boolean = true

    companion object {
        private fun findAnnotation(context: ExtensionContext): io.microkt.kontainers.junit5.annotation.Kontainer =
            context.testClass.get().getAnnotation(io.microkt.kontainers.junit5.annotation.Kontainer::class.java)
    }
}
