package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.junit5.annotation.DatabaseKontainer
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.reflect.KClass

/**
 * Provides an opinionated [JUnit Jupiter extension](https://junit.org/junit5/docs/current/user-guide/#extensions)
 * capable of running a single database Kontainer for integration testing.
 *
 * @author Scott Rossillo
 * @see io.microkt.kontainers.junit5.annotation.DatabaseKontainer
 */
class DatabaseKontainerExtension : AbstractBeforeAfterKontainerExtension() {

    override fun kontainerKClass(context: ExtensionContext): KClass<out Kontainer> =
        findAnnotation(context).value

    override fun propertySuppliers(context: ExtensionContext): Array<KClass<out PropertySupplier>> =
        findAnnotation(context).propertySuppliers

    override fun useDefaultPropertySuppliers(context: ExtensionContext): Boolean =
        findAnnotation(context).useDefaultPropertySuppliers

    override suspend fun isReady(kontainer: Kontainer): Boolean =
        when (kontainer) {
            is JdbcKontainer -> kontainer.isDatabaseReady()
            else -> throw IllegalStateException("Expected a JDBC Kontainer")
        }

    companion object {
        private fun findAnnotation(context: ExtensionContext): DatabaseKontainer =
            context.testClass.get().getAnnotation(DatabaseKontainer::class.java)
    }
}
