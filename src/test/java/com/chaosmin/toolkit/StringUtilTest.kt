package com.chaosmin.toolkit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author romani
 * @since 2019-07-29 17:14
 */
class StringUtilTest {
    @Test
    fun upperCamel() {
        assertEquals("legend_never_died", StringUtil.upperCamel("LegendNeverDied"))
        assertEquals("a_b_c", StringUtil.upperCamel("a_b_c"))
        assertEquals("a_b_c", StringUtil.upperCamel("aBC"))
        assertEquals("a#_a&a", StringUtil.upperCamel("a#A&a"))
    }

    @Test
    fun lowerUnderscore() {
        assertEquals("aAaA", StringUtil.lowerUnderscore("a_aa_a"))
        assertEquals("legendNeverDied", StringUtil.lowerUnderscore("legend_never_died"))
        assertEquals("aBC", StringUtil.lowerUnderscore("a_b_c"))
        assertEquals("abc", StringUtil.lowerUnderscore("aBc"))
        assertEquals("a#A&a", StringUtil.lowerUnderscore("a#_a&a"))
    }

    @Test
    fun isNumber() {
        assertTrue(StringUtil.isNumber("15"))
        assertTrue(StringUtil.isNumber("1.5"))
        assertTrue(StringUtil.isNumber("1.50"))
        assertTrue(StringUtil.isNumber("01.5"))
        assertFalse(StringUtil.isNumber("a"))
        assertFalse(StringUtil.isNumber("1&"))
        assertFalse(StringUtil.isNumber("1.a"))
    }

    @Test
    fun removeNonNumeric() {
        assertEquals("15", StringUtil.removeNonNumeric("abc1d5g"))
        assertEquals("1.5", StringUtil.removeNonNumeric("1.5"))
        assertEquals("1.5", StringUtil.removeNonNumeric("q$1.rw5o&"))
    }
}