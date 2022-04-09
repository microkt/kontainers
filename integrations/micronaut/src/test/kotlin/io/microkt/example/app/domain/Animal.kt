package io.microkt.example.app.domain

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
class Animal(
    @field:Id
    @GeneratedValue
    val id: Long,
    val name: String
)
