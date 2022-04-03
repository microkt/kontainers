package io.microkt.kontainers.mongo

import io.microkt.kontainers.junit5.annotation.Kontainers
import io.microkt.kontainers.keycloak.KeycloakKontainer
import io.microkt.kontainers.keycloak.keycloakKontainerSpec
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class KeycloakKontainerTest(private val keycloakKontainer: KeycloakKontainer) {

    @Test
    fun testValidPort() {
        assertNotNull(keycloakKontainer.getPort(keycloakKontainerSpec.ports.first().port))
    }
}
