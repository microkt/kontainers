package io.microkt.kontainers.domain.readiness

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FixedDelayReadinessProbe(
    private val time: Long
) : ReadinessProbe {
    override fun waitUntilReady(timeout: Long) = runBlocking {
        withContext(Dispatchers.Unconfined) {
            delay(time.coerceAtMost(timeout))
        }
    }
}
