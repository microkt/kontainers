package io.microkt.kontainers.domain

interface JdbcKontainer : Kontainer {
    val driverClassName: String
    fun createJdbcUrl(): String
    fun getUsername(): String
    fun getPassword(): String
}
