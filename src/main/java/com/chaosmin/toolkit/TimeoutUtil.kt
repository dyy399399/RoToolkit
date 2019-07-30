package com.chaosmin.toolkit

import java.util.concurrent.TimeUnit

/**
 * @author romani
 * @since 2019-07-30 11:52
 */
object TimeoutUtil {
    fun toSeconds(timeout: Long, unit: TimeUnit): Long {
        return roundUpIfNecessary(timeout, unit.toSeconds(timeout))
    }

    fun toMillis(timeout: Long, unit: TimeUnit): Long {
        return roundUpIfNecessary(timeout, unit.toMillis(timeout))
    }

    private fun roundUpIfNecessary(timeout: Long, convertedTimeout: Long): Long {
        return if (timeout > 0 && convertedTimeout == 0L) 1
        else convertedTimeout
    }
}