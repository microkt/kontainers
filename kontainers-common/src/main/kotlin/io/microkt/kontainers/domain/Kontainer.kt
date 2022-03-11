package io.microkt.kontainers.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Provides a platform-agnostic method for interacting with containers defined
 * by a [Kontainer specification][KontainerSpec].
 *
 * @author Scott Rossillo
 * @see KontainerSpec
 */
interface Kontainer {
    /**
     * The platform assigned identifier for this Kontainer.
     */
    val id: String

    /**
     * The [Kontainer specification][KontainerSpec] for this Kontainer.
     */
    val kontainerSpec: KontainerSpec

    /**
     * Starts this Kontainer asynchronously with the given [timeout].
     *
     * @param timeout the maximum amount of time, in milliseconds, to wait
     * for the this [Kontainer] to start; defaults to 30 seconds
     */
    suspend fun start(timeout: Long = 30_000)

    /**
     * Starts this Kontainer synchronously with the given [timeout], blocking
     * the current thread until this Kontainer starts or the [timeout] is reached.
     *
     * @param timeout the maximum amount of time, in milliseconds, to wait
     * for the this [Kontainer] to start; defaults to 30 seconds
     */
    fun startSync(timeout: Long = 30_000) {
        runBlocking {
            launch(Dispatchers.IO) { start(timeout) }
        }
    }

    /**
     * Removes this Kontainer for the running platform. Once a Kontainer is
     * removed, it cannot be restarted.
     */
    suspend fun remove()

    /**
     * Removes this Kontainer synchronously from the running platform.
     *
     * @see remove
     */
    fun removeSync() = runBlocking { remove() }

    /**
     * Returns the platform assigned default port for this Kontainer. The
     * default port is the first port specified by this Kontainer's [KontainerSpec].
     */
    fun getPort(): Int?

    /**
     * Returns the platform assigned port mapped to the given exposed [containerPort]
     * defined by this Kontainer's [KontainerSpec].
     */
    fun getPort(containerPort: Int): Int?

    /**
     * Returns the platform assigned address at which this Kontainer can be reached
     * the application that started this Kontainer. The returned address may be an IP
     * address or hostname depending on the platform on which it's running.
     */
    fun getAddress(): String?

    /**
     * Returns the platform assigned address at which this Kontainer can be directly
     * reached by other Kontainers running on the platform.
     */
    fun getDirectAddress(): String?
}
