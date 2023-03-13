package ru.otus.otuskotlin.marketplace.serverlessapp.api.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplRequestId
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import yandex.cloud.sdk.functions.Context
import java.util.*

/**
 * Input:    /v1/ad/create?
 * Output:  ad/create
 */
const val PATH_SEPARATOR = "/"
fun String.dropVersionPrefix(versionPrefix: String) =
    split(PATH_SEPARATOR)
        .filter { it.isNotEmpty() && it != versionPrefix }
        .joinToString(PATH_SEPARATOR)
        .let { if (it.endsWith("?")) it.dropLast(1) else it }


val objectMapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules()

inline fun <reified T> Request.toTransportModel(): T =
    if (isBase64Encoded) {
        objectMapper.readValue(Base64.getDecoder().decode(body))
    } else {
        objectMapper.readValue(body)
    }

fun withContext(context: Context, block: MkplContext.() -> Response) =
    with(
        MkplContext(
            timeStart = Clock.System.now(),
            requestId = MkplRequestId(context.requestId)
        )
    ) {
        block()
    }

/**
 * V1
 */
fun ru.otus.otuskotlin.marketplace.api.v1.models.IResponse.toResponse(): Response = convert()

/**
 * V2
 */
fun ru.otus.otuskotlin.marketplace.api.v2.models.IResponse.toResponse(): Response = convert()

private fun <IResponse> IResponse.convert(): Response =
    Response(
        200,
        false,
        mapOf("Content-Type" to "application/json"),
        objectMapper.writeValueAsString(this),
    )