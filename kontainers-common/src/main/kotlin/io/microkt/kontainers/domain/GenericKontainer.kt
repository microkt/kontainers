package io.microkt.kontainers.domain

import io.microkt.kontainers.runner.KontainerRunner

/**
 * Provides an abstract [Kontainer] implementation to handle common
 * Kontainer actions. Delegates to a platform specific [parent] Kontainer
 * to run the given [kontainerSpec].
 *
 * @author Scott Rossillo
 */
abstract class GenericKontainer(
    override val kontainerSpec: KontainerSpec,
    private val delegate: PlatformKontainer
) : Kontainer by delegate {

    final override suspend fun start(timeout: Long) {
        delegate.start(timeout)
        waitForReady(timeout)
    }

    /**
     * Blocks until this [Kontainer] is ready to accept requests.
     */
    abstract fun waitForReady(timeout: Long)
}
