package io.microkt.kontainers.domain

/**
 * Provides an abstract [Kontainer] implementation to handle common
 * Kontainer actions. Delegates to a platform specific [parent] Kontainer
 * to run the given [kontainerSpec].
 *
 * @author Scott Rossillo
 */
abstract class GenericKontainer(
    override val kontainerSpec: KontainerSpec,
    private val parent: Kontainer
) : Kontainer {
    final override val id: String
        get() = parent.id

    final override suspend fun start(timeout: Long) {
        parent.start(timeout)
        waitForReady(timeout)
    }

    /**
     * Blocks until this [Kontainer] is ready to accept requests.
     */
    abstract fun waitForReady(timeout: Long)

    override suspend fun remove() = parent.remove()

    final override fun getPort(): Int? = parent.getPort()

    final override fun getPort(containerPort: Int): Int? = parent.getPort(containerPort)

    final override fun getAddress(): String? = parent.getAddress()

    final override fun getDirectAddress(): String? = parent.getDirectAddress()
}
