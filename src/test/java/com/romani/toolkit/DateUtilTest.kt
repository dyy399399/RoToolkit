package com.romani.toolkit

import org.junit.jupiter.api.Test

/**
 * @author romani
 * @since 2019-07-22 11:14
 */
class DateUtilTest {
    @Test
    fun now() {
        println(DateUtil.now())
    }

    @Test
    fun today() {
        println(DateUtil.today())
    }

    @Test
    fun parse() {
        println(DateUtil.parse("2019-07-21"))
        println(DateUtil.parse("2019-07-21", DateUtil.DATE_FORMAT))
        println(DateUtil.parse("2019-07-21", "yyyy-MM-dd"))

        println(DateUtil.parse("2019-07-21 12:00:00"))
        println(DateUtil.parse("2019-07-21 12:00:00", DateUtil.DATETIME_FORMAT))
        println(DateUtil.parse("2019-07-21 12:00:00", "yyyy-MM-dd HH:mm:ss"))
    }

    @Test
    fun format() {
        val date = DateUtil.now()
        println(DateUtil.format(date))
        println(DateUtil.format(date, DateUtil.DATETIME_FORMAT))
        println(DateUtil.format(date, "YYYY-MM-dd hh:mm:ss"))
    }

    @Test
    fun cutDate(){
        val date = DateUtil.now()
        println(DateUtil.cutDate(date))
    }

    @Test
    fun addDays() {
        val date = DateUtil.now()
        val addOneDay = DateUtil.addDays(date, 1)
        println("$date add one day: $addOneDay")
        val minusOneDay = DateUtil.addDays(date, -1)
        println("$date minus one day: $minusOneDay")
    }
}