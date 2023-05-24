package ru.otus.otuskotlin.marketplace.auth

import io.ktor.client.request.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.module
import ru.otus.otuskotlin.marketplace.app.moduleJvm
import ru.otus.otuskotlin.marketplace.app.ru.otus.otuskotlin.marketplace.auth.addAuth
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        application {
            module(authConfig = KtorAuthConfig.TEST)
            moduleJvm(authConfig = KtorAuthConfig.TEST)
        }

        val response = client.post("/v1/ad/create") {
            addAuth(config = KtorAuthConfig.TEST.copy(audience = "invalid"))
        }
        assertEquals(401, response.status.value)
    }
}
