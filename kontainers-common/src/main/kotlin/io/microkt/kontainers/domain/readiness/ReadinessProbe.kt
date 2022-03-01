package io.microkt.kontainers.domain.readiness

interface ReadinessProbe {
    fun waitUntilReady(timeout: Long)
}
