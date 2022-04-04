package io.microkt.kontainers.junit5

import io.microkt.kontainers.domain.KontainerSpec

interface KontainerProvider {
    fun override(kontainerSpec: KontainerSpec): KontainerSpec
}
