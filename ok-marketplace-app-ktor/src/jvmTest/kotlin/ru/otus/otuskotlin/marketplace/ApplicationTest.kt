package ru.otus.otuskotlin.marketplace

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.app.moduleJvm
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `root endpoint`() = testApplication {
        application { moduleJvm() }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
