package ru.otus.otuskotlin.marketplace.builders

import ru.otus.otuskotlin.marketplace.builders.kotlin.Drink
import ru.otus.otuskotlin.marketplace.builders.kotlin.breakfast
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinBuilderTestCase {
    @Test
    fun `test building java-like breakfast`() {
        val breakfast = breakfast {
            eggs = 3 // BreakfastBuilder().eggs = 3
            bacon = true
            title = "Simple"
            drink = Drink.COFFEE
        }

        assertEquals(3, breakfast.eggs)
        assertEquals(true, breakfast.bacon)
        assertEquals("Simple", breakfast.title)
        assertEquals(Drink.COFFEE, breakfast.drink)
    }
}
