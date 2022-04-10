package io.microkt.kontainers.context.spec

import io.microkt.kontainers.domain.KontainerSpec

interface KontainerSpecProvider {
    fun override(kontainerSpec: KontainerSpec): KontainerSpec
}
