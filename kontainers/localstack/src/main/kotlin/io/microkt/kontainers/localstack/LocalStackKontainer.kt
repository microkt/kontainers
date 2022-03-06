package io.microkt.kontainers.localstack

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class LocalStackKontainer(
    override val kontainerSpec: KontainerSpec,
    parentHandle: Kontainer
) : GenericTcpKontainer(kontainerSpec, parentHandle) {

    object Default {
        const val IMAGE = "localstack/localstack"
        const val IMAGE_LIGHT = "localstack/localstack-light"
        const val PORT = 4566
        const val VERSION = "0.14"
    }
}
