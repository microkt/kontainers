package io.microkt.kontainers.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("unit")
internal class KontainerMemoryKtTest {

    @Test
    fun megabyteConversion() {
        assertEquals(MEGABYTES.toULong(), 1.MB)
        assertEquals(MEGABYTES.toULong(), 1.0.MB)
        assertEquals(MEGABYTES.toULong(), 1.toLong().MB)
    }

    @Test
    fun gigabyteConversion() {
        assertEquals(GIGABYTES.toULong(), 1.GB)
        assertEquals(GIGABYTES.toULong(), 1.0.GB)
        assertEquals(1181116006.toULong(), 1.1.GB)
        assertEquals(536870912.toULong(), 0.5.GB)
        assertEquals(GIGABYTES.toULong(), 1.toLong().GB)
    }
}
