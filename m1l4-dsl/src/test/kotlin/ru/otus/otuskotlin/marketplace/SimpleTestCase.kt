package ru.otus.otuskotlin.marketplace

import org.junit.Test
import kotlin.test.assertEquals

class SimpleTestCase {
    @Test
    fun `my test`() {
        sout {
            1 + 123
        }
    }

    @Test
    fun `sout with prefix`() {
        soutWithTimestamp {
            "${time()}: my line."
        }
    }

    @Test
    fun `dsl functions`() {
        val (key, value) = Pair("key", "value")
        assertEquals(key, "key")
        assertEquals(value, "value")

        val pairNew = "key" to "value"
        assertEquals(pairNew.first, "key")
        assertEquals(pairNew.second, "value")

        val myTimeOld = "12".time("30")
        assertEquals(myTimeOld, "12:30")

        val myTime = "12" time "30"
        assertEquals(myTime, "12:30")
    }
}
