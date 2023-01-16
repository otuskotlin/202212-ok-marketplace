package ru.otus.otuskotlin.marketplace

import org.junit.Test
import ru.otus.otuskotlin.marketplace.user.dsl.buildUser
import ru.otus.otuskotlin.marketplace.user.models.Action
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserTestCase {
    @Test
    fun `test user`() {
        val user = buildUser {
            name {
                first = "Kirill"
                last = "Krylov"
            }
            contacts {
                email = "email@gmail.com"
                phone = "81234567890"
            }
            actions {
                add(Action.UPDATE)
                add(Action.ADD)

                +Action.DELETE
                +Action.READ
            }
            availability {
                monday("11:30")
                friday("18:00")
            }
        }

        assertTrue(user.id.isNotEmpty())
        assertEquals("Kirill", user.firstName)
        assertEquals("", user.secondName)
        assertEquals("Krylov", user.lastName)
        assertEquals("email@gmail.com", user.email)
        assertEquals("81234567890", user.phone)
        assertEquals(setOf(Action.UPDATE, Action.ADD, Action.DELETE, Action.READ), user.actions)
        assertEquals(2, user.available.size)
    }
}
