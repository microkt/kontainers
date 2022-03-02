package io.microkt.kontainers.runner

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory

abstract class AbstractKontainerFactory<T : Kontainer> : KontainerFactory<T> {
    protected val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }
    override fun createKontainer(): T = this.createKontainer(kontainerSpec)
}
