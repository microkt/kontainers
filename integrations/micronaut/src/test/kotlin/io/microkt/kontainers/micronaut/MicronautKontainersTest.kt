package io.microkt.kontainers.micronaut

import io.microkt.example.app.repository.AnimalRepository
import io.microkt.kontainers.junit5.annotation.DatabaseKontainer
import io.microkt.kontainers.postgresql.PostgresKontainer
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
@DatabaseKontainer(PostgresKontainer::class)
@MicronautTest
class MicronautKontainersTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    lateinit var repository: AnimalRepository

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
        Assertions.assertEquals(6, repository.count())
    }
}
