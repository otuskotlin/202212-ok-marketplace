package ru.otus.otuskotlin.marketplace.serverlessapp.api

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.*
import yandex.cloud.sdk.functions.Context

interface IHandleStrategy {
    val version: String
    val path: String
    fun handle(req: Request, reqContext: Context): Response
}