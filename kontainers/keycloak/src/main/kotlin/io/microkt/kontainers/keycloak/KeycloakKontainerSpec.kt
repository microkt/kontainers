package io.microkt.kontainers.keycloak

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.keycloak.KeycloakKontainer.Env.KEYCLOAK_ADMIN
import io.microkt.kontainers.keycloak.KeycloakKontainer.Env.KEYCLOAK_ADMIN_PASSWORD

/**
 * Default Keycloak [kontainerSpec].
 *
 * @author Scott Rossillo
 * @sample [io.microkt.kontainers.keycloak.keycloakKontainerSpec]
 */
val keycloakKontainerSpec = kontainerSpec {
    name = "keycloak"
    image = "quay.io/keycloak/keycloak:17.0.1"
    command = listOf("start-dev")
    environment {
        set(KEYCLOAK_ADMIN to "admin")
        set(KEYCLOAK_ADMIN_PASSWORD to "admin")
    }
    ports {
        expose tcp 27017
    }
    resources {
        limit memory 512.MB
    }
}
