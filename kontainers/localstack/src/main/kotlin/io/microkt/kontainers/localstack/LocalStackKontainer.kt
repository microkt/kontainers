package io.microkt.kontainers.localstack

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec

/**
 * Provides a [Kontainer] for [LocalStack](https://localstack.cloud/).
 *
 * @author Scott Rossillo
 */
class LocalStackKontainer(
    override val kontainerSpec: KontainerSpec,
    parentHandle: Kontainer
) : GenericTcpKontainer(kontainerSpec, parentHandle) {

    /**
     * Provides [LocalStackKontainer] defaults.
     */
    object Default {
        /**
         * Default `localstack/localstack` image.
         */
        const val IMAGE = "localstack/localstack"

        /**
         * Default LocalStack light image `localstack/localstack-light`.
         */
        const val IMAGE_LIGHT = "localstack/localstack-light"

        /**
         * Default LocalStack port `4566`.
         */
        const val PORT = 4566

        /**
         * Default LocalStack version `0.14`.
         */
        const val VERSION = "0.14"
    }
}
