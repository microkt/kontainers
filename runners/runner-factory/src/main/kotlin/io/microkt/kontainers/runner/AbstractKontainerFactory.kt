package io.microkt.kontainers.runner

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory

/**
 * Provides an abstract [KontainerFactory] for [Kontainer]s of type [T].
 *
 * @author Scott Rossillo
 */
abstract class AbstractKontainerFactory<T : Kontainer> : KontainerFactory<T> {
    /**
     * The [KontainerRunner] for the current platform.
     */
    protected val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }

    /**
     * Creates a [Kontainer] [T] with the default [kontainerSpec].
     *
     * @see createKontainer
     */
    override fun createKontainer(): T = this.createKontainer(kontainerSpec)
}
