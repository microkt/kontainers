package io.microkt.kontainers.mysql

import io.microkt.kontainers.junit5.annotation.KontainerSpecOverride
import io.microkt.kontainers.junit5.annotation.Kontainers
import io.microkt.kontainers.localstack.LocalstackKontainer
import io.microkt.kontainers.localstack.localstackKontainerSpec
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class LocalstackKontainerTest(
    @KontainerSpecOverride(
        image = "${LocalstackKontainer.Default.IMAGE_LIGHT}:${LocalstackKontainer.Default.VERSION}",
        environment = ["SERVICES=sqs"]
    )
    private val localstack: LocalstackKontainer
) {
    @Test
    fun testValidPort() {
        Assertions.assertNotNull(localstack.getPort(localstackKontainerSpec.ports.first().port))
    }
}
