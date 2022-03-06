package io.microkt.kontainers.localstack

import io.microkt.kontainers.dsl.kontainerSpec

val localStackKontainerSpec = kontainerSpec {
    name = "localstack"
    image = "${LocalStackKontainer.Default.IMAGE}:${LocalStackKontainer.Default.VERSION}"
    ports {
        expose tcp LocalStackKontainer.Default.PORT
    }
}
