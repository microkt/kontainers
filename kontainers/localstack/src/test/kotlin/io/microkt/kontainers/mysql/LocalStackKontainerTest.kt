package io.microkt.kontainers.mysql

import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride
import io.microkt.kontainers.junit5.annotation.Kontainers
import io.microkt.kontainers.localstack.LocalStackKontainer
import io.microkt.kontainers.localstack.LocalStackKontainer.Env.SERVICES
import io.microkt.kontainers.localstack.localStackKontainerSpec
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class LocalStackKontainerTest(
    @KontainerSpecOverride(
        environment = ["$SERVICES=sqs"]
    )
    private val localstack: LocalStackKontainer
) {
    @Test
    fun testValidPort() {
        Assertions.assertNotNull(localstack.getPort(localStackKontainerSpec.ports.first().port))
    }
}
