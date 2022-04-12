package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.runner.retryOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace
import java.util.UUID
import kotlin.reflect.KClass

/**
 *
 * @author Scott Rossillo
 */
abstract class AbstractBeforeAfterKontainerExtension : BeforeAllCallback, AfterAllCallback {
    private val namespace = Namespace.create(AbstractBeforeAfterKontainerExtension::class, UUID.randomUUID())

    abstract fun kontainerKClass(context: ExtensionContext): KClass<out Kontainer>

    open fun createKontainer(context: ExtensionContext): Kontainer {
        val factory = findFactory(kontainerKClass(context))
        return factory!!.createKontainer()
    }

    abstract fun propertySuppliers(context: ExtensionContext): Array<KClass<out PropertySupplier>>
    abstract fun useDefaultPropertySuppliers(context: ExtensionContext): Boolean

    abstract suspend fun isReady(kontainer: Kontainer): Boolean

    private fun getNamespace(context: ExtensionContext): Namespace = namespace

    final override fun beforeAll(context: ExtensionContext) {
        val kontainer: Kontainer = createKontainer(context)

        runBlocking(Dispatchers.IO) {
            kontainer.start()
            retryOperation<Unit>(retries = 10) {
                if (!isReady(kontainer)) {
                    operationFailed()
                }
            }
        }

        context.getStore(getNamespace(context)).put(KONTAINER, kontainer)
        kontainer.populateEnvironment(
            customPropertySuppliers = listOf(*propertySuppliers(context)),
            useDefaultPropertySuppliers = useDefaultPropertySuppliers(context)
        )
    }

    override fun afterAll(context: ExtensionContext) {
        val kontainer: Kontainer = context.getStore(getNamespace(context)).get(KONTAINER) as Kontainer
        kontainer.removeSync()
    }

    companion object {
        private const val KONTAINER = "kontainer"
    }
}
