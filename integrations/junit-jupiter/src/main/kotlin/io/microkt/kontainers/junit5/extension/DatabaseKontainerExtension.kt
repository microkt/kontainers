package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.JdbcKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.junit5.annotation.DatabaseKontainer
import io.microkt.kontainers.runner.retryOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Provides a JUnit Jupiter extension capable of running a single database
 * Kontainer for integration testing.
 *
 * @author Scott Rossillo
 */
class DatabaseKontainerExtension : BeforeAllCallback, AfterAllCallback, AbstractKontainerExtension() {
    private val ns: ExtensionContext.Namespace = ExtensionContext.Namespace.create(DatabaseKontainerExtension::class)

    private fun isReady(kontainer: Kontainer): Boolean =
        when (kontainer) {
            is JdbcKontainer -> kontainer.isDatabaseReady()
            else -> throw IllegalStateException("Expected a JDBC Kontainer")
        }

    override fun beforeAll(context: ExtensionContext) {
        val ann = findAnnotation(context)
        val factory = findFactory(ann.value)
        val kontainer: Kontainer = factory!!.createKontainer()

        runBlocking(Dispatchers.IO) {
            kontainer.start()
            retryOperation<Unit>(retries = 10) {
                if (!isReady(kontainer)) {
                    operationFailed()
                }
            }
        }

        kontainerStarted(kontainer)
        context.getStore(ns).put(KONTAINER, kontainer)
    }

    override fun afterAll(context: ExtensionContext) {
        val kontainer: Kontainer = context.getStore(ns).get(KONTAINER) as Kontainer
        kontainer.removeSync()
    }

    companion object {
        private const val KONTAINER = "kontainer"

        private fun findAnnotation(context: ExtensionContext): DatabaseKontainer =
            context.testClass.get().getAnnotation(DatabaseKontainer::class.java)
    }
}
