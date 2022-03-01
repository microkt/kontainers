package io.microkt.kontainers.runner

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class RetryOperationTest {

    interface Worker { suspend fun work(): String? }

    @Test
    fun retriesTheRightNumberOfTimes() {
        val worker: Worker = mockk()
        coEvery { worker.work() } returns null

        runBlocking {
            retryOperation<String?>(retries = 10, initialIntervalMilli = 5, maxDelay = 10) {
                worker.work() ?: operationFailed()
            }
        }
        coVerify(exactly = 11) { worker.work() }
    }

    @Test
    fun retriesTheRightNumberOfTimesWhenOne() {
        val worker: Worker = mockk()
        coEvery { worker.work() } returns null

        runBlocking {
            retryOperation<String?>(retries = 1, initialIntervalMilli = 5, maxDelay = 10) {
                worker.work() ?: operationFailed()
            }
        }

        coVerify(exactly = 2) { worker.work() }
    }

    @Test
    fun returnsWhenExpectationMet() {
        val worker: Worker = mockk()
        coEvery { worker.work() } returns null coAndThen { "hello" }

        runBlocking {
            retryOperation<String?>(retries = 10, initialIntervalMilli = 5, maxDelay = 10) {
                worker.work() ?: operationFailed()
            }
        }
        coVerify(exactly = 2) { worker.work() }
    }
}
