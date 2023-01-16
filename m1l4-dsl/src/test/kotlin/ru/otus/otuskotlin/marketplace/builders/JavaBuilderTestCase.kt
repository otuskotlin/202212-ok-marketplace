package ru.otus.otuskotlin.marketplace.builders

import ru.otus.otuskotlin.marketplace.builders.java.BreakfastBuilder
import ru.otus.otuskotlin.marketplace.builders.java.Drink
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaBuilderTestCase {
    @Test
    fun `test building java-like breakfast`() {
        val breakfast = BreakfastBuilder()
            .withEggs(3)
            .withBacon(true)
            .withTitle("Simple")
            .withDrink(Drink.COFFEE)
            .build()

        assertEquals(3, breakfast.eggs)
        assertEquals(true, breakfast.bacon)
        assertEquals("Simple", breakfast.title)
        assertEquals(Drink.COFFEE, breakfast.drink)
    }
}
