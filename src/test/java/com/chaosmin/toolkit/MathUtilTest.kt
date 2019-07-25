package com.chaosmin.toolkit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

/**
 * @author romani
 * @since 2019-07-25 16:58
 */
class MathUtilTest {
    @Test
    fun parseToDouble() {
        assertEquals(0.0, MathUtil.parseToDouble(""))
        assertEquals(1.0, MathUtil.parseToDouble("1"))
        assertEquals(1.1, MathUtil.parseToDouble("1.1"))
        assertEquals(0.0, MathUtil.parseToDouble("1.e", true))
        assertThrows(NumberFormatException::class.java) { MathUtil.parseToDouble("1.e") }
    }

    @Test
    fun parseEngineeringString() {
        assertEquals(0.0, MathUtil.parseEngineeringString(""))
        assertEquals(1.1, MathUtil.parseEngineeringString("1.1"))
        assertEquals(120.0, MathUtil.parseEngineeringString("1.2E2"))
    }
}