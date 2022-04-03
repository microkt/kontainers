package io.microkt.kontainers.keycloak

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.AbstractKontainerFactory
import kotlin.reflect.KClass

/**
 * Provides a [MongoKontainer][KontainerFactory].
 *
 * @author Scott Rossillo
 */
class KeycloakKontainerFactory : AbstractKontainerFactory<KeycloakKontainer>(), KontainerFactory<KeycloakKontainer> {
    override val kontainerSpec: KontainerSpec
        get() = keycloakKontainerSpec

    override fun createKontainer(kontainerSpec: KontainerSpec): KeycloakKontainer {
        return KeycloakKontainer(
            kontainerSpec = kontainerSpec,
            delegate = runner.createSync(kontainerSpec)
        )
    }

    override fun supports(kontainerKClass: KClass<out Kontainer>): Boolean =
        kontainerKClass == KeycloakKontainer::class
}
