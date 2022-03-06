package io.microkt.kontainers.localstack

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

class LocalstackKontainer(
    override val kontainerSpec: KontainerSpec,
    private val parentHandle: Kontainer
) : GenericTcpKontainer(kontainerSpec, parentHandle) {

    object Default {
        const val IMAGE = "localstack/localstack"
        const val IMAGE_LIGHT = "localstack/localstack-light"
        const val PORT = 4566
        const val VERSION = "0.14"
    }

    object Env {
        const val MYSQL_DATABASE = "MYSQL_DATABASE"
        const val MYSQL_USER = "MYSQL_USER"
        const val MYSQL_PASSWORD = "MYSQL_PASSWORD"
        const val MYSQL_ROOT_PASSWORD = "MYSQL_ROOT_PASSWORD"
    }
}
