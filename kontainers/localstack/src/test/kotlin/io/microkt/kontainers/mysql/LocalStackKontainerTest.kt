package io.microkt.kontainers.mysql

import io.microkt.kontainers.context.spec.KontainerSpecOverride
import io.microkt.kontainers.context.spec.KontainerSpecProvider
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.junit5.annotation.Kontainers
import io.microkt.kontainers.localstack.LocalStackKontainer
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
    @KontainerSpecOverride(LocalStackCustomSpecProvider::class)
    private val localstack: LocalStackKontainer
) {
    @Test
    fun testValidPort() {
        Assertions.assertNotNull(localstack.getPort(localStackKontainerSpec.ports.first().port))
    }

    companion object {
        class LocalStackCustomSpecProvider : KontainerSpecProvider {
            override fun override(kontainerSpec: KontainerSpec): KontainerSpec =
                kontainerSpec(kontainerSpec) {
                    environment {
                        set("SERVICES" to "sqs")
                    }
                }
        }
    }
}
