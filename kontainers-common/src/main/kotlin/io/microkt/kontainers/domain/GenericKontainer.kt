package io.microkt.kontainers.domain

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

    abstract fun waitForReady(timeout: Long)

    override suspend fun remove() = parent.remove()

    final override fun getPort(): Int? = parent.getPort()

    final override fun getPort(containerPort: Int): Int? = parent.getPort(containerPort)

    final override fun getAddress(): String? = parent.getAddress()

    final override fun getDirectAddress(): String? = parent.getDirectAddress()
}