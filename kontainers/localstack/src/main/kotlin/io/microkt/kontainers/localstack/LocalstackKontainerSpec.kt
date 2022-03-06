package io.microkt.kontainers.localstack

import io.microkt.kontainers.dsl.kontainerSpec

val localstackKontainerSpec = kontainerSpec {
    name = "localstack"
    image = "${this.name}:${LocalstackKontainer.Default.VERSION}"
    ports {
        expose tcp LocalstackKontainer.Default.PORT
    }
}
