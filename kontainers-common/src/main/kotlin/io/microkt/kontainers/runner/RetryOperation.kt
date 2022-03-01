package io.microkt.kontainers.runner

import kotlinx.coroutines.delay

class RetryOperation<T> internal constructor(
    private val retries: Int,
    private val initialIntervalMilli: Long,
    private val maxDelay: Long,
    private val retry: suspend RetryOperation<T>.() -> T?
) {
    var tryNumber: Int = 0
        internal set

    suspend fun operationFailed(): T? {
        if (++tryNumber <= retries) {
            delay(calculateDelay(tryNumber, initialIntervalMilli, maxDelay))
            return retry.invoke(this)
        }
        return null
    }
}

suspend fun <T> retryOperation(
    retries: Int = 100,
    initialDelay: Long = 0,
    initialIntervalMilli: Long = 5000,
    maxDelay: Long = 20000,
    operation: suspend RetryOperation<T>.() -> T
): T {
    val retryOperation = RetryOperation(
        retries,
        initialIntervalMilli,
        maxDelay,
        operation,
    )

    delay(initialDelay)

    return operation.invoke(retryOperation)
}

internal fun calculateDelay(tryNumber: Int, initialIntervalMilli: Long, maxDelay: Long): Long =
    (initialIntervalMilli * tryNumber).toLong().coerceAtMost(maxDelay)
