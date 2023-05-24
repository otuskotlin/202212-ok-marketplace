package ru.otus.otuskotlin.marketplace.app

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.helpers.testSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `root endpoint`() = testApplication {
        application { module(testSettings(), authConfig = KtorAuthConfig.TEST) }
        val response = client.get("/")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }
}
