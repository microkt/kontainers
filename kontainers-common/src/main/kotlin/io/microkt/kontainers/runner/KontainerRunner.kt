package io.microkt.kontainers.runner

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface KontainerRunner {
    suspend fun create(kontainerSpec: KontainerSpec): Kontainer
    suspend fun delete(kontainerId: String)
    suspend fun start(kontainerId: String)

    fun createSync(kontainerSpec: KontainerSpec): Kontainer =
        runBlocking {
            withContext(Dispatchers.IO) { create(kontainerSpec) }
        }
}
