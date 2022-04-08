package io.microkt.kontainers.context.properties

import io.microkt.kontainers.domain.Kontainer

interface PropertySupplier {
    fun supply(kontainer: Kontainer): Map<String, String>
}
