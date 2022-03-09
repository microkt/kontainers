package io.microkt.kontainers.localstack

import io.microkt.kontainers.dsl.kontainerSpec

/**
 * [KontainerSpec] for a [LocalStackKontainer].
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.localstack.localStackKontainerSpec]
 */
val localStackKontainerSpec = kontainerSpec {
    name = "localstack"
    image = "localstack/localstack:0.14"
    ports {
        expose tcp 4566
    }
}

/**
 * [KontainerSpec] for a [LocalStackKontainer] light.
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.localstack.localStackLightKontainerSpec]
 */
val localStackLightKontainerSpec = kontainerSpec(localStackKontainerSpec) {
    image = "localstack/localstack-light:0.14"
}
