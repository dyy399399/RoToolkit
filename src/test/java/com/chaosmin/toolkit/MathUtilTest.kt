package com.chaosmin.toolkit

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author romani
 * @since 2019-07-25 16:58
 */
class MathUtilTest {
    @Test
    fun parseToDouble() {
        assertEquals(0.0, MathUtil.parseToDouble(""), 0.0)
        assertEquals(1.0, MathUtil.parseToDouble("1"), 0.0)
        assertEquals(1.1, MathUtil.parseToDouble("1.1"), 0.0)
        assertEquals(0.0, MathUtil.parseToDouble("1.e", true), 0.0)
//        assertThrows(NumberFormatException::class.java) { MathUtil.parseToDouble("1.e") }
    }

    @Test
    fun parseEngineeringString() {
        assertEquals(0.0, MathUtil.parseEngineeringString(""), 0.0)
        assertEquals(1.1, MathUtil.parseEngineeringString("1.1"), 0.0)
        assertEquals(120.0, MathUtil.parseEngineeringString("1.2E2"), 0.0)
    }
}