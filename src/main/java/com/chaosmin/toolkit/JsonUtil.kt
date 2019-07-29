package com.chaosmin.toolkit

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.*
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Json Toolkit based on Jackson
 *
 * @author romani
 * @since 2019-07-26 15:55
 */
object JsonUtil {
    val objectMapper = ObjectMapper()

    init {
        resetConfigure()
    }

    /**
     * reset configure of objectMapper
     */
    fun resetConfigure(): ObjectMapper {
        return this.objectMapper.apply {
            findAndRegisterModules()
            // allow Java style annotation
            configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            // allow Yaml style annotation
            configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)
            // allow single quotes
            configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            // allow end commas
            configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true)
            // formatted output
            configure(SerializationFeature.INDENT_OUTPUT, false)
            dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA) as DateFormat
        }
    }

    /**
     * see [ObjectMapper.configure]
     */
    fun configure(f: JsonParser.Feature, state: Boolean): ObjectMapper {
        return this.objectMapper.configure(f, state)
    }

    /**
     * see [ObjectMapper.configure]
     */
    fun configure(f: SerializationFeature, state: Boolean): ObjectMapper {
        return this.objectMapper.configure(f, state)
    }

    /**
     * format object to string
     * @param obj object need to be formatted
     * @param prettyPrinter format string in pretty printer
     */
    fun encode(obj: Any?, prettyPrinter: Boolean = false): String {
        return if (obj == null) ""
        else try {
            if (!prettyPrinter) objectMapper.writeValueAsString(obj)
            else objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * parse string to object
     * @param json string need to be parsed
     * @param T javaType of object
     */
    inline fun <reified T> decode(json: String?): T? {
        if (json.isNullOrBlank()) return null
        return try {
            objectMapper.readValue<T>(json)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * parse string to list
     * @param json string need to be parsed
     * @param T javaType of element in list
     */
    inline fun <reified T> parseToList(json: String?): List<T> {
        return if (json.isNullOrBlank()) emptyList()
        else {
            val javaType = objectMapper.typeFactory.constructParametricType(List::class.java, T::class.java)
            objectMapper.readValue(json, javaType)
        }
    }

    /**
     * parse string to map
     * @param json string need to be parsed
     * @param K javaType of key in map
     * @param V javaType of value in map
     */
    inline fun <reified K, reified V> parseToMap(json: String?): Map<K, V> {
        return if (json.isNullOrBlank()) emptyMap()
        else {
            val javaType = objectMapper.typeFactory.constructParametricType(Map::class.java, K::class.java, V::class.java)
            objectMapper.readValue(json, javaType)
        }
    }

    /**
     * convert [JsonNode]'s value with judgment of node type, if node type is [MissingNode] then return null
     * @param node jsonNode need to be converted
     */
    inline fun <reified T> convertValue(node: JsonNode): T? {
        return if (node is MissingNode) null
        else objectMapper.convertValue<T>(node)
    }

    /**
     * create basic JsonNode within designated JavaType
     */
    private fun <T> basicNode(value: T): JsonNode {
        return when (value) {
            is String -> TextNode(value)
            is Int -> IntNode(value)
            is Long -> LongNode(value)
            is Double -> DoubleNode(value)
            is Boolean -> BooleanNode.valueOf(value)
            else -> POJONode(value)
        }
    }

    /**
     * create a new JsonNode by ObjectMapper
     */
    fun newNode(): JsonNode {
        return objectMapper.createObjectNode()
    }

    /**
     * create a new ArrayNode by ObjectMapper
     */
    fun newArrayNode(): JsonNode {
        return objectMapper.createArrayNode()
    }

    /**
     * create a new JsonNode with key and value by ObjectMapper
     * @param key key of node
     * @param value value of node
     */
    fun <T> newNode(key: String, value: T): JsonNode {
        return objectMapper.createObjectNode().set(key, basicNode(value))
    }

    /**
     * create a new ArrayNode with key and value by ObjectMapper
     * @param value value of node(must be list)
     */
    fun <T> newArrayNode(value: List<T>): JsonNode {
        return objectMapper.createArrayNode().addAll(value.mapNotNull { basicNode(it) })
    }
}