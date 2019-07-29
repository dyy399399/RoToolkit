package com.chaosmin.toolkit

import com.google.common.base.CaseFormat
import java.util.regex.Pattern

/**
 * @author romani
 * @since 2019-07-29 17:05
 */
object StringUtil {
    private val isNumber = Pattern.compile("^[-+]?[.\\d]*$")
    private val number = Pattern.compile("")

    /**
     * convert a string from Hump format to Underline format
     * @param str string need to be converted
     */
    fun upperCamel(str: String): String {
        return if (str.isBlank()) str
        else CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str)
    }

    /**
     * convert a string from Underline format to Hump format
     * @param str string need to be converted
     */
    fun lowerUnderscore(str: String): String {
        return if (str.isBlank()) str
        else CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str)
    }

    /**
     * determine whether a string is a number
     * @param str string need to be judged
     */
    fun isNumber(str: String): Boolean {
        return isNumber.matcher(str).matches()
    }

    /**
     * get number in a string by remove all non-numeric character
     * @param str string need to be parsed
     */
    fun removeNonNumeric(str: String): String {
        val regEx = "[^0-9.]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.replaceAll("").trim { it <= ' ' }
    }
}