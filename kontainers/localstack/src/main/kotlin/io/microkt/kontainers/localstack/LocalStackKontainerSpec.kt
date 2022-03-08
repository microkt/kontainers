package io.microkt.kontainers.localstack

import io.microkt.kontainers.dsl.kontainerSpec

/**
 * [KontainerSpec] for a [LocalStackKontainer].
 *
 * @author Scott Rossillo
 */
val localStackKontainerSpec = kontainerSpec {
    name = "localstack"
    image = "${LocalStackKontainer.Default.IMAGE}:${LocalStackKontainer.Default.VERSION}"
    ports {
        expose tcp LocalStackKontainer.Default.PORT
    }
}
