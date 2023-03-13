package ru.otus.otuskotlin.marketplace.serverlessapp.api.v1

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.dropVersionPrefix
import yandex.cloud.sdk.functions.Context

const val VERSION_1_PREFIX = "v1"

fun v1Handlers(event: Request, context: Context): Response =
    when (event.url.dropVersionPrefix(VERSION_1_PREFIX)) {
        "ad/create" -> createAdHandler.handle(event, context)
        "ad/read"   -> readAdHandler.handle(event, context)
        "ad/update" -> updateAdHandler.handle(event, context)
        "ad/delete" -> deleteAdHandler.handle(event, context)
        "ad/search" -> searchAdHandler.handle(event, context)
        "ad/offers" -> offersAdHandler.handle(event, context)
        else -> Response(400, false, emptyMap(), "Unknown path! Path: ${event.url}")
    }