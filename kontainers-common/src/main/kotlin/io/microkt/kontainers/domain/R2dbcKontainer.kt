package io.microkt.kontainers.domain

interface R2dbcKontainer : JdbcKontainer {
    val r2dbcDriverName: String
    fun createR2dbcUrl(): String
}
