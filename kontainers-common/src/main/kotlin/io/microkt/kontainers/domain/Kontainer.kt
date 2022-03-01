package io.microkt.kontainers.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface Kontainer {
    val id: String
    val kontainerSpec: KontainerSpec

    suspend fun start(timeout: Long = 30_000)
    fun startSync() {
        runBlocking {
            launch(Dispatchers.IO) { start() }
        }
    }

    suspend fun remove()
    fun removeSync() = runBlocking { remove() }

    fun getPort(): Int?
    fun getPort(containerPort: Int): Int?
    fun getAddress(): String?
    fun getDirectAddress(): String?
}
