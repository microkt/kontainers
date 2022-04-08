package io.microkt.example.app.domain

import org.springframework.data.annotation.Id

data class Animal(
    @Id
    val id: Int,
    val name: String
)
