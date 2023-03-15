package ru.otus.otuskotlin.marketplace.serverlessapp.api

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.IV1HandleStrategy.Companion.V1
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.v1handlers
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.IV2HandleStrategy.Companion.V2
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.v2handlers
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction

@Suppress("unused")
class RoutingHandler : YcFunction<Request, Response> {

    override fun handle(event: Request, context: Context): Response =
        try {
            println(event)
            val validationResponse = validate(event)
            val url = event.url!!
            when {
                validationResponse != null -> validationResponse
                url.isVersion(V1) -> v1handlers(event, context)
                url.isVersion(V2) -> v2handlers(event, context)
                else -> Response(400, false, emptyMap(), "Unknown api version! Path: ${event.url}")
            }
        } catch (e: Exception) {
            Response(500, false, emptyMap(), "Unknown error: ${e.message}")
        }

    /**
     * Validate input event.
     */
    private fun validate(event: Request): Response? =
        when {
            event.httpMethod != "POST" -> Response(400, false, emptyMap(), "Invalid http method: ${event.httpMethod}")
            event.url == null -> Response(400, false, emptyMap(), "Invalid url")
            event.body == null -> Response(400, false, emptyMap(), "Invalid body")
            else -> null
        }

    private fun String.isVersion(versionPrefix: String): Boolean = startsWith("/$versionPrefix")
}
