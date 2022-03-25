package io.microkt.kontainers.domain

fun Double.toKontainerBytes(multiplier: Long): ULong = times(multiplier).toULong()
fun Long.toKontainerBytes(multiplier: Long): ULong = times(multiplier).toULong()
fun Int.toKontainerBytes(multiplier: Long): ULong = times(multiplier).toULong()

const val MEGABYTES: Long = 1048576
const val GIGABYTES: Long = 1073741824

inline val Double.MB get() = toKontainerBytes(MEGABYTES)
inline val Double.GB get() = toKontainerBytes(GIGABYTES)

inline val Long.MB get() = toKontainerBytes(MEGABYTES)
inline val Long.GB get() = toKontainerBytes(GIGABYTES)

inline val Int.MB get() = toKontainerBytes(MEGABYTES)
inline val Int.GB get() = toKontainerBytes(GIGABYTES)
