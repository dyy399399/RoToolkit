package com.chaosmin.toolkit

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * @author romani
 * @since 2019-07-26 16:22
 */
class JsonUtilTest {

    class TestClass {
        var name: String? = null
        var age: Int? = null
    }

    @Test
    fun resetConfigure() {
        val obj = TestClass().apply {
            this.name = "romani"
            this.age = 24
        }
        assertEquals("""{"name":"romani","age":24}""", JsonUtil.encode(obj))
        JsonUtil.configure(SerializationFeature.INDENT_OUTPUT, true)
        JsonUtil.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, false)
        assertEquals("{\n  \"name\" : \"romani\",\n  \"age\" : 24\n}", JsonUtil.encode(obj))
        JsonUtil.resetConfigure()
        assertEquals("""{"name":"romani","age":24}""", JsonUtil.encode(obj))
    }

    @Test
    fun encode() {
        val obj = TestClass().apply {
            this.name = "romani"
            this.age = 24
        }
        assertEquals("""{"name":"romani","age":24}""", JsonUtil.encode(obj))
        assertEquals("{\n  \"name\" : \"romani\",\n  \"age\" : 24\n}", JsonUtil.encode(obj, true))
    }

    @Test
    fun decode() {
        val obj = JsonUtil.decode<TestClass>("""{"name":"romani","age":24}""")
        assertEquals("romani", obj?.name)
        val list = JsonUtil.decode<List<TestClass>>("""[{"name":"romani","age":24}]""")
        assertEquals("romani", list?.firstOrNull()?.name)
    }

    @Test
    fun parseToList() {
        val listEmp = JsonUtil.parseToList<TestClass>("")
        assertEquals(0, listEmp.size)

        val list = JsonUtil.parseToList<TestClass>("""[{"name":"romani","age":24}]""")
        assertEquals("romani", list.firstOrNull()?.name)

        val lists = JsonUtil.parseToList<List<String>>("""[["1"],["2","3"],["4"]]""")
        assertEquals(3, lists.size)
        assertEquals(2, lists[1].size)
    }

    @Test
    fun parseToMap() {
        val mapEmp = JsonUtil.parseToMap<String, String>("")
        assertEquals(0, mapEmp.size)

        val mapSS = JsonUtil.parseToMap<String, String>("""{"1":"1"}""")
        assertEquals("1", mapSS.keys.firstOrNull())
        assertEquals("1", mapSS.values.firstOrNull())

        val mapSI = JsonUtil.parseToMap<String, Int>("""{"1":"1"}""")
        assertEquals("1", mapSI.keys.firstOrNull())
        assertEquals(1, mapSI.values.firstOrNull())

        val maps = JsonUtil.parseToMap<String, Map<String, String>>("""{"name":{"first":"min","second":"chao"}}""")
        assertEquals("chao", maps["name"]?.get("second"))
    }

    @Test
    fun newNode() {
        assertEquals(ObjectNode::class.java, JsonUtil.newNode().javaClass)
        assertEquals(TextNode::class.java, JsonUtil.newNode("name", "romani")["name"].javaClass)
    }

    @Test
    fun newArrayNode() {
        assertEquals(0, JsonUtil.newArrayNode().size())
        assertEquals(3, JsonUtil.newArrayNode(listOf("ro", "ma", "ni")).size())
    }

    @Test
    fun convertValue() {
        val missingNode = JsonUtil.newNode().path("notExists")
        assertNull(JsonUtil.convertValue<Any>(missingNode))
        val objNode = JsonUtil.newNode("name", "romani").path("name")
        assertEquals("romani", JsonUtil.convertValue(objNode))
    }
}