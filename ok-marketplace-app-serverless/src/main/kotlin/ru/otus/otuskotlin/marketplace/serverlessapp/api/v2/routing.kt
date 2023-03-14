package ru.otus.otuskotlin.marketplace.serverlessapp.api.v2


import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.dropVersionPrefix
import yandex.cloud.sdk.functions.Context

fun v2handlers(req: Request, reqContext: Context): Response =
    IV2HandleStrategy.strategiesByDiscriminator[req.url!!.dropVersionPrefix(IV2HandleStrategy.V2)]
        ?.handle(req, reqContext)
        ?: Response(400, false, emptyMap(), "Unknown path! Path: ${req.url}")