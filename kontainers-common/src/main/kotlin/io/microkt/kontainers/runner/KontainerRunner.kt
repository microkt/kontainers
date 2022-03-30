package io.microkt.kontainers.runner

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Defines methods to be implemented by any platform capable of
 * running [Kontainer]s.
 *
 * @author Scott Rossillo
 */
interface KontainerRunner {
    /**
     * Creates a new [Kontainer] from the given [kontainerSpec].
     */
    suspend fun create(kontainerSpec: KontainerSpec): PlatformKontainer

    /**
     * Deletes the [Kontainer] with the given, platform assigned [kontainerId].
     */
    suspend fun delete(kontainerId: String)

    /**
     * Starts the [Kontainer] with the given, platform assigned [kontainerId].
     *
     * @see create
     */
    suspend fun start(kontainerId: String)

    /**
     * Synchronously creates a [Kontainer] from the given [kontainerSpec] on
     * [Dispatchers.IO] while blocking the current thread.
     */
    fun createSync(kontainerSpec: KontainerSpec): PlatformKontainer =
        runBlocking {
            withContext(Dispatchers.IO) { create(kontainerSpec) }
        }
}
