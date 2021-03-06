package io.microkt.kontainers.junit5

import io.microkt.kontainers.context.spec.KontainerSpecProvider
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.dsl.kontainerSpec

class RedisBusterSpecProvider : KontainerSpecProvider {
    override fun override(kontainerSpec: KontainerSpec): KontainerSpec =
        kontainerSpec(kontainerSpec) {
            image = "redis:6.2-buster"
        }
}
