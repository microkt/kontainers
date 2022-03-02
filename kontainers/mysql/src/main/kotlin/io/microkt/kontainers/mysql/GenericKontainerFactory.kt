package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerFactory
import io.microkt.kontainers.runner.KontainerRunner
import io.microkt.kontainers.runner.KontainerRunnerFactory

abstract class GenericKontainerFactory<T : Kontainer> : KontainerFactory<T> {
    protected val runner: KontainerRunner by lazy { KontainerRunnerFactory.createRunner() }
    override fun createKontainer(): T = this.createKontainer(kontainerSpec)
}
