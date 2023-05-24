package ru.otus.otuskotlin.marketplace.app.ru.otus.otuskotlin.marketplace.auth

import io.ktor.client.request.*
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig

expect fun HttpRequestBuilder.addAuth(
    id: String = "user1",
    groups: List<String> = listOf("USER", "TEST"),
    config: KtorAuthConfig = KtorAuthConfig.TEST,
)
