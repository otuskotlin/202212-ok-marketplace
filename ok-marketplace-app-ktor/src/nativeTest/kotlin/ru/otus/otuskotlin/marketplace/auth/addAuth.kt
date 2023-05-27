package ru.otus.otuskotlin.marketplace.app.ru.otus.otuskotlin.marketplace.auth

import io.ktor.client.request.*
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig

actual fun HttpRequestBuilder.addAuth(
    id: String,
    groups: List<String>,
    config: KtorAuthConfig
) {
}