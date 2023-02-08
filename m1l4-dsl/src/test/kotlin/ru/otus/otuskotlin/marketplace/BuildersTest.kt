package ru.otus.otuskotlin.marketplace

import org.junit.Test
import ru.otus.otuskotlin.marketplace.builders.java.BreakfastBuilder
import ru.otus.otuskotlin.marketplace.builders.java.Drink
import ru.otus.otuskotlin.marketplace.builders.kotlin.breakfast
import kotlin.test.assertEquals
import ru.otus.otuskotlin.marketplace.builders.java.Drink as JDrink
import ru.otus.otuskotlin.marketplace.builders.kotlin.Drink as KDrink

class BuildersTest {
    @Test
    fun `java breakfast builder test`() {
        val breakfast = BreakfastBuilder()
            .withEggs(3)
            .withBacon(true)
            .withTitle("Simple")
            .withDrink(JDrink.COFFEE)
            .build()

        assertEquals(3, breakfast.eggs)
        assertEquals(true, breakfast.bacon)
        assertEquals("Simple", breakfast.title)
        assertEquals(Drink.COFFEE, breakfast.drink)
    }

    @Test
    fun `kotlin breakfast builder test`() {
        val breakfast = breakfast {
            eggs = 3 // BreakfastBuilder().eggs = 3
            bacon = true
            title = "Simple"
            drink = KDrink.COFFEE
        }

        assertEquals(3, breakfast.eggs)
        assertEquals(true, breakfast.bacon)
        assertEquals("Simple", breakfast.title)
        assertEquals(ru.otus.otuskotlin.marketplace.builders.kotlin.Drink.COFFEE, breakfast.drink)
    }
}
