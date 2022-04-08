package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.junit5.annotation.DatabaseKontainer
import mu.KotlinLogging
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.ServiceLoader
import kotlin.reflect.KClass

/**
 *
 */
class DatabaseKontainerExtension : BeforeAllCallback, AfterAllCallback {
    private val log = KotlinLogging.logger { }
    private val ns: ExtensionContext.Namespace = ExtensionContext.Namespace.create(DatabaseKontainerExtension::class)

    override fun beforeAll(context: ExtensionContext) {
        val ann = findAnnotation(context)
        val factory = findKFactory(ann.value)
        val kontainer: Kontainer = factory!!.createKontainer()

        kontainer.startSync()
        kontainerStarted(kontainer)

        context.getStore(ns).put(KONTAINER, kontainer)
    }

    override fun afterAll(context: ExtensionContext) {
        val kontainer: Kontainer = context.getStore(ns).get(KONTAINER) as Kontainer
        kontainer.removeSync()
    }

    private fun kontainerStarted(kontainer: Kontainer) {
        PropertySupplier::class.sealedSubclasses.forEach { supplier ->
            supplier.objectInstance!!.supply(kontainer).forEach { (k, v) ->
                log.info { "setting sys prop $k=$v" }
                System.setProperty(k, v)
            }
        }
    }

    companion object {
        const val KONTAINER = "kontainer"

        private val loader = lazy { ServiceLoader.load(KontainerFactory::class.java) }

        private fun findAnnotation(context: ExtensionContext): DatabaseKontainer =
            context.testClass.get().getAnnotation(DatabaseKontainer::class.java)

        private fun findKFactory(kontainerKClass: KClass<out JdbcKontainer>): KontainerFactory<out Kontainer>? =
            loader.value.firstOrNull { it.supports(kontainerKClass) }
    }
}
