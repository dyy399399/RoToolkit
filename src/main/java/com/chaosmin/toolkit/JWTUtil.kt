package com.chaosmin.toolkit

import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import net.minidev.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author romani
 * @since 2019-07-30 11:00
 */
object JWTUtil {
    /**
     * Using Symmetric Encryption Algorithms 'HS256' to create token
     */
    fun createTokenHS256(payloadMap: Map<String, Any>, secret: ByteArray, timeout: Long = 0, unit: TimeUnit = TimeUnit.SECONDS): String {
        val jwsHeader = JWSHeader(JWSAlgorithm.HS256)
        val payload = getPayload(payloadMap, timeout, unit)
        val jwsObject = JWSObject(jwsHeader, payload)
        val jwsSigner = MACSigner(secret)
        jwsObject.sign(jwsSigner)
        return jwsObject.serialize()
    }

    /**
     * Using Asymmetric Encryption Algorithms 'RS256' to create token
     */
    fun createTokenRS256(payloadMap: Map<String, Any>, rsaJWK: RSAKey, timeout: Long = 0, unit: TimeUnit = TimeUnit.SECONDS): String {
        val jwsHeader = JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.keyID).build()
        val payload = getPayload(payloadMap, timeout, unit)
        val jwsObject = JWSObject(jwsHeader, payload)
        val jwsSigner = RSASSASigner(rsaJWK)
        jwsObject.sign(jwsSigner)
        return jwsObject.serialize()
    }

    /**
     * verifier token by Symmetric Encryption Algorithms 'HS256'
     */
    fun validTokenHS256(token: String, secret: ByteArray): Map<String, Any> {
        val jwsObject = JWSObject.parse(token)
        val jwsVerifier = MACVerifier(secret)
        return verify(jwsObject, jwsVerifier)
    }

    /**
     * verifier token by Symmetric Encryption Algorithms 'RS256'
     */
    fun validTokenRS256(token: String, rsaJWK: RSAKey): Map<String, Any> {
        val jwsObject = JWSObject.parse(token)
        val jwsVerifier = RSASSAVerifier(rsaJWK.toPublicJWK())
        return verify(jwsObject, jwsVerifier)
    }

    /**
     * generate RSA key
     */
    fun generateRSAKey(): RSAKey {
        return RSAKeyGenerator(2048).generate()
    }

    private fun getPayload(payloadMap: Map<String, Any>, timeout: Long, unit: TimeUnit): Payload {
        return if (timeout > 0) {
            val expireMap = payloadMap.toMutableMap()
            expireMap["exp"] = Date().time + TimeoutUtil.toMillis(timeout, unit)
            Payload(JSONObject(expireMap))
        } else Payload(JSONObject(payloadMap))
    }

    /**
     * verify token by using verifier
     * @param jwsObject token object
     * @param jwsVerifier verifier
     */
    @Throws(JOSEException::class)
    private fun verify(jwsObject: JWSObject, jwsVerifier: JWSVerifier): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        val payload = jwsObject.payload
        if (jwsObject.verify(jwsVerifier)) {
            val jsonObject = payload.toJSONObject()
            result["status"] = 1
            result["data"] = jsonObject
            if (jsonObject.containsKey("exp")) {
                val expTime = jsonObject["exp"].toString().toLong()
                if (expTime <= Date().time) {
                    result.clear()
                    result["status"] = 2
                    result["message"] = "token is expired."
                }
            }
        } else {
            result["status"] = 0
            result["message"] = "token is error."
        }
        return result
    }
}