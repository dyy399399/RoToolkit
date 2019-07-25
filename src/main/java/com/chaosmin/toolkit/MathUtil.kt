package com.chaosmin.toolkit

import java.math.BigDecimal

/**
 * @author romani
 * @since 2019-07-25 16:49
 */
object MathUtil {
    fun parseToDouble(obj: Any, ignore: Boolean = false): Double {
        val str = obj.toString()
        return if (str.isBlank()) 0.0
        else {
            try {
                str.toDouble()
            } catch (e: NumberFormatException) {
                if (ignore) 0.0
                else throw e
            }
        }
    }

    fun parseEngineeringString(str: String): Double {
        return when {
            str.isBlank() -> 0.0
            str.contains("E", true) -> BigDecimal(str).toDouble()
            else -> parseToDouble(str)
        }
    }
}