package io.microkt.kontainers.spring

import io.microkt.example.app.KontainersDemoApplication
import io.microkt.example.app.domain.Animal
import io.microkt.example.app.repository.ReactiveAnimalRepository
import io.microkt.kontainers.junit5.annotation.DatabaseKontainer
import io.microkt.kontainers.postgresql.PostgresKontainer
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query

@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
@SpringBootTest(classes = [KontainersDemoApplication::class])
@DatabaseKontainer(PostgresKontainer::class)
class KontainersDemoApplicationTests {
    @Autowired
    private lateinit var template: R2dbcEntityTemplate

    @Autowired
    private lateinit var animalRepository: ReactiveAnimalRepository

    @Test
    fun contextLoads() {
        runBlocking {
            val animal = template.select<Animal>()
                .matching(query(where("name").`is`("cat")))
                .awaitFirst()
            assertEquals("cat", animal.name)

            assertEquals("dog", animalRepository.findByName("dog").awaitFirst().name)
        }
    }
}
