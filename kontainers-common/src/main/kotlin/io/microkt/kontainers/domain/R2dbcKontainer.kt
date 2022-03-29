package io.microkt.kontainers.domain

interface R2dbcKontainer : DatabaseKontainer {
    fun createR2dbcUrl(): String
}
