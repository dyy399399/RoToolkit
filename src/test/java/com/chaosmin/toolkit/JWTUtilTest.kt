package com.chaosmin.toolkit

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * @author romani
 * @since 2019-07-30 11:38
 */
class JWTUtilTest {
    @Test
    fun testHS256Token() {
        val map = mapOf("name" to "romani")
        val secret = "geiwodiangasfdjsikolkjikolkijswe".toByteArray()
        val tokenHS256 = JWTUtil.createTokenHS256(map, secret)
        val decodeMap = JWTUtil.validTokenHS256(tokenHS256, secret)
        assertEquals(1, decodeMap["status"])
        assertEquals("romani", JsonUtil.parseToMap<String, String>(decodeMap["data"].toString())["name"])
    }

    @Test
    fun testHS256TokenExpired() {
        val map = mapOf("name" to "romani")
        val secret = "geiwodiangasfdjsikolkjikolkijswe".toByteArray()
        val tokenHS256 = JWTUtil.createTokenHS256(map, secret, 3, TimeUnit.SECONDS)
        Thread.sleep(3000)
        val decodeMap = JWTUtil.validTokenHS256(tokenHS256, secret)
        assertEquals(2, decodeMap["status"])
    }

    @Test
    fun testRS256Token() {
        val map = mapOf("name" to "romani")
        val rsaKey = JWTUtil.generateRSAKey()
        val tokenRS256 = JWTUtil.createTokenRS256(map, rsaKey)
        val decodeMap = JWTUtil.validTokenRS256(tokenRS256, rsaKey)
        assertEquals(1, decodeMap["status"])
        assertEquals("romani", JsonUtil.parseToMap<String, String>(decodeMap["data"].toString())["name"])
    }

    @Test
    fun testRS256TokenExpired() {
        val map = mapOf("name" to "romani")
        val rsaKey = JWTUtil.generateRSAKey()
        val tokenRS256 = JWTUtil.createTokenRS256(map, rsaKey, 3, TimeUnit.SECONDS)
        Thread.sleep(3000)
        val decodeMap = JWTUtil.validTokenRS256(tokenRS256, rsaKey)
        assertEquals(2, decodeMap["status"])
    }
}