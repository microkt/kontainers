package io.microkt.kontainers.localstack

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer

/**
 * Provides a [Kontainer] for [LocalStack](https://localstack.cloud/).
 *
 * @author Scott Rossillo
 */
class LocalStackKontainer(
    override val kontainerSpec: KontainerSpec,
    delegate: PlatformKontainer
) : GenericTcpKontainer(kontainerSpec, delegate) {
    companion object Env {
        const val SERVICES = "SERVICES"
    }
}
