package io.microkt.kontainers.keycloak

import io.microkt.kontainers.domain.GenericTcpKontainer
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer

/**
 * Provides a Keycloak [Kontainer].
 *
 * @author Scott Rossillo
 * @constructor Creates a new [KeycloakKontainer] with the given [kontainerSpec].
 * @sample io.microkt.kontainers.mongo.KeycloakKontainerTest
 */
class KeycloakKontainer(
    override val kontainerSpec: KontainerSpec,
    delegate: PlatformKontainer
) : GenericTcpKontainer(kontainerSpec, delegate) {
    /**
     * MongoDB environment variables.
     */
    object Env {
        const val KEYCLOAK_ADMIN = "KEYCLOAK_ADMIN"
        const val KEYCLOAK_ADMIN_PASSWORD = "KEYCLOAK_ADMIN_PASSWORD"
    }
}
