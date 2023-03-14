package ru.otus.otuskotlin.marketplace.serverlessapp.api.v1

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.dropVersionPrefix
import yandex.cloud.sdk.functions.Context

fun v1handlers(req: Request, reqContext: Context): Response =
    IV1HandleStrategy.strategiesByDiscriminator[req.url!!.dropVersionPrefix(IV1HandleStrategy.V1)]
        ?.handle(req, reqContext)
        ?: Response(400, false, emptyMap(), "Unknown path! Path: ${req.url}")