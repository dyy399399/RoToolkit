package com.romani.toolkit

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author romani
 * @since 2018-12-10 17:46
 */
object DateUtil {
    private const val DATE_PATTERN = "yyyy-MM-dd"
    private const val DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    val DATE_FORMAT = SimpleDateFormat(DATE_PATTERN, Locale.CHINA)
    val DATETIME_FORMAT = SimpleDateFormat(DATETIME_PATTERN, Locale.CHINA)

    /**
     * current time
     */
    fun now(): Date = Date()

    /**
     * start of today
     */
    fun today(): Date = LocalDate().toDate()

    private fun formatter(pattern: String, locale: Locale): SimpleDateFormat = SimpleDateFormat(pattern, locale)

    /**
     * parse string to java date, default pattern is 'yyyy-MM-dd'
     */
    fun parse(source: String, pattern: String = "", locale: Locale = Locale.CHINA): Date {
        return when {
            source.isBlank() -> Date()
            pattern.isBlank() -> DATE_FORMAT.parse(source)
            else -> formatter(pattern, locale).parse(source)
        }
    }

    fun parse(source: String, sdf: SimpleDateFormat): Date = sdf.parse(source)

    /**
     * format java date to string, default pattern is 'yyyy-MM-dd'
     */
    fun format(date: Date, pattern: String = "", locale: Locale = Locale.CHINA): String {
        return when {
            pattern.isBlank() -> DATE_FORMAT.format(date)
            else -> formatter(pattern, locale).format(date)
        }
    }

    fun format(date: Date, sdf: SimpleDateFormat): String = sdf.format(date)

    /**
     * cut date only get date part
     */
    fun cutDate(date: Date): Date {
        return LocalDate.fromDateFields(date).toDate()
    }

    /**
     * add days on date
     */
    fun addDays(date: Date, days: Int): Date {
        val ldt = LocalDateTime.fromDateFields(date)
        return ldt.plusDays(days).toDate()
    }
}
