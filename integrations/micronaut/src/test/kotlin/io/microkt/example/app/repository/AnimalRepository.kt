package io.microkt.example.app.repository

import io.microkt.example.app.domain.Animal
import io.micronaut.data.annotation.Id
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
interface AnimalRepository : CrudRepository<Animal, Long> {
    fun update(@Id id: Long, name: String)
}
