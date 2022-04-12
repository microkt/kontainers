package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.context.properties.PropertySupplier
import io.microkt.kontainers.domain.Kontainer
import mu.KotlinLogging
import java.util.ServiceLoader
import kotlin.reflect.KClass

private val propertySupplierLoader = lazy { ServiceLoader.load(PropertySupplier::class.java) }

internal fun Kontainer.populateEnvironment(
    customPropertySuppliers: List<KClass<out PropertySupplier>>,
    useDefaultPropertySuppliers: Boolean = true
) {
    val log = KotlinLogging.logger { }

    if (useDefaultPropertySuppliers) {
        propertySupplierLoader.value.forEach { supplier ->
            supplier.supply(this).forEach { (k, v) ->
                log.info { "setting sys prop $k=$v" }
                System.setProperty(k, v)
            }
        }
    }

    customPropertySuppliers.map { supplier: KClass<out PropertySupplier> ->
        supplier.constructors.first { it.parameters.isEmpty() }.call()
    }.forEach { supplier ->
        supplier.supply(this).forEach { (k, v) ->
            log.info { "setting sys prop $k=$v" }
            System.setProperty(k, v)
        }
    }
}
