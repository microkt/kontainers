package io.microkt.example.app.repository

import io.microkt.example.app.domain.Animal
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ReactiveAnimalRepository : ReactiveCrudRepository<Animal, Int> {
    fun findByName(name: String): Mono<Animal>
}
