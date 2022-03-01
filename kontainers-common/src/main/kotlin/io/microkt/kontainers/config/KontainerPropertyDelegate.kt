package io.microkt.kontainers.config

import mu.KotlinLogging
import java.io.File
import java.util.Properties
import kotlin.reflect.KProperty

object KontainerPropertyDelegate {
    private val log = KotlinLogging.logger { }
    private val props: Map<String, String> = createPropStore()

    val optional = Optional()
    val required = Required()

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String? = props[prop.name]

    class Optional {
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): String? = props[prop.name]
    }

    class Required {
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): String = props[prop.name]!!
    }

    private enum class PropertyKey(val propName: String, vararg val envNames: String) {
        NAMESPACE(propName = "namespace", "KONTAINERS_NAMESPACE", "KONTAINER_NAMESPACE"),
        RUNNER_NAME(propName = "runnerName", "KONTAINERS_RUNNER", "KONTAINER_RUNNER"),
        MINIKUBE_IP(propName = "minikubeIp", "MINIKUBE_IP")
    }

    private fun createPropStore(): Map<String, String> {
        val props = readConfig()
        val propStore: MutableMap<String, String> = mutableMapOf()

        PropertyKey.values().forEach { key ->
            key.envNames.forEach { envName ->
                val value: String? = props.getProperty(key.propName, System.getenv(envName))
                if (value != null) {
                    log.debug { "Initialized property ${key.propName}=$value" }
                    propStore[key.propName] = value
                }
            }
        }

        return propStore
    }

    private fun readConfig(): Properties {
        val propsFile = File(System.getProperty("user.home"), ".kontainers.props")
        val props = Properties()

        if (propsFile.isFile && propsFile.canRead()) {
            props.load(propsFile.inputStream())
        } else {
            io.microkt.kontainers.config.KontainerPropertyDelegate.log.warn { "Unable to load configuration properties from ~/.kontainers.props" }
        }

        return props
    }
}
