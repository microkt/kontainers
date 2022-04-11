package io.microkt.kontainers.context.properties

import io.microkt.kontainers.domain.Kontainer

/**
 * Supplies properties for a framework's configuration such
 * as Spring Boot or Micronaut.
 *
 * @author Scott Rossillo
 */
interface PropertySupplier {
    /**
     * Supplies environment properties for the given [kontainer].
     *
     * @param kontainer the [Kontainer] for which to supply properties
     * @return a [Map] of properties for the given [Kontainer] if this
     * [PropertySupplier] can supply properties; an empty [Map] otherwise
     */
    fun supply(kontainer: Kontainer): Map<String, String>
}
