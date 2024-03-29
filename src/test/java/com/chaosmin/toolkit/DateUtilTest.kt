package com.chaosmin.toolkit

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author romani
 * @since 2019-07-22 11:14
 */
class DateUtilTest {
    @Test
    fun now() {
//        println(DateUtil.now())
    }

    @Test
    fun today() {
//        println(DateUtil.today())
    }

    @Test
    fun parse() {
//        println(DateUtil.parse("2019-07-21"))
//        println(DateUtil.parse("2019-07-21", DateUtil.DATE_FORMAT))
//        println(DateUtil.parse("2019-07-21", "yyyy-MM-dd"))
//        println(DateUtil.parse("2019-07-21 12:00:00"))
//        println(DateUtil.parse("2019-07-21 12:00:00", DateUtil.DATETIME_FORMAT))
//        println(DateUtil.parse("2019-07-21 12:00:00", "yyyy-MM-dd HH:mm:ss"))
    }

    @Test
    fun format() {
//        val date = DateUtil.now()
//        println(DateUtil.format(date))
//        println(DateUtil.format(date, DateUtil.DATETIME_FORMAT))
//        println(DateUtil.format(date, "YYYY-MM-dd hh:mm:ss"))
    }

    @Test
    fun cutDate() {
//        val date = DateUtil.now()
//        println(DateUtil.cutDate(date))
    }

    @Test
    fun addDays() {
//        val date = DateUtil.now()
//        val addOneDay = DateUtil.addDays(date, 1)
//        println("$date add one day: $addOneDay")
//        val minusOneDay = DateUtil.addDays(date, -1)
//        println("$date minus one day: $minusOneDay")
    }

    @Test
    fun sameDate() {
        val d1 = DateUtil.parse("2019-07-25 10:00:00", DateUtil.DATETIME_FORMAT)
        val d2 = DateUtil.parse("2019-07-25 06:10:00", DateUtil.DATETIME_FORMAT)
        assertTrue(DateUtil.sameDate(d1, d2))

        val d3 = DateUtil.parse("2019-07-28 10:00:00", DateUtil.DATETIME_FORMAT)
        assertFalse(DateUtil.sameDate(d1, d3))
    }
}