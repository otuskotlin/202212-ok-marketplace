package ru.otus.otuskotlin.marketplace.serverlessapp.api

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.VERSION_1_PREFIX
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.v1Handlers
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.VERSION_2_PREFIX
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.v2Handlers
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction

@Suppress("unused")
class RoutingHandler : YcFunction<Request, Response> {

    override fun handle(event: Request, context: Context): Response {
        return try {
            val validationResponse = validate(event)
            when {
                validationResponse != null -> validationResponse
                event.url.isVersion(VERSION_1_PREFIX) -> v1Handlers(event, context)
                event.url.isVersion(VERSION_2_PREFIX) -> v2Handlers(event, context)
                else -> Response(400, false, emptyMap(), "Unknown api version! Path: ${event.url}")
            }
        } catch (e: Exception) {
            Response(500, false, emptyMap(), "Unknown error: ${e.message}")
        }
    }

    /**
     * Validate input event.
     */
    private fun validate(event: Request): Response? =
        when {
            event.httpMethod != "POST" -> Response(400, false, emptyMap(), "Invalid http method: ${event.httpMethod}")
            else -> null
        }

    private fun String.isVersion(versionPrefix: String): Boolean = startsWith("/$versionPrefix")
}
