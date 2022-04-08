package io.microkt.kontainers.context.spec

import io.microkt.kontainers.domain.KontainerSpec

interface KontainerProvider {
    fun override(kontainerSpec: KontainerSpec): KontainerSpec
}
