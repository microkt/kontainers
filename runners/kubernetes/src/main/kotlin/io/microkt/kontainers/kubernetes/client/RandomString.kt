package io.microkt.kontainers.kubernetes.client

private const val STRING_LENGTH = 10
private val charPool: List<Char> = ('a'..'z') + ('0'..'9')

internal fun randomString() = (1..STRING_LENGTH)
    .map { kotlin.random.Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")
