package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.app.process
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.mappers.v2.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

suspend fun ApplicationCall.createAd(appSettings: MkplAppSettings) {
    val request = apiV2Mapper.decodeFromString<AdCreateRequest>(receiveText())
    val context = MkplContext()
    context.fromTransport(request)
    process(context)
    respond(apiV2Mapper.encodeToString(context.toTransportCreate()))
}

suspend fun ApplicationCall.readAd(appSettings: MkplAppSettings) {
    val request = apiV2Mapper.decodeFromString<AdReadRequest>(receiveText())
    val context = MkplContext()
    context.fromTransport(request)
    process(context)
    respond(apiV2Mapper.encodeToString(context.toTransportRead()))
}

suspend fun ApplicationCall.updateAd(appSettings: MkplAppSettings) {
    val request = apiV2Mapper.decodeFromString<AdUpdateRequest>(receiveText())
    val context = MkplContext()
    context.fromTransport(request)
    process(context)
    respond(apiV2Mapper.encodeToString(context.toTransportUpdate()))
}

suspend fun ApplicationCall.deleteAd(appSettings: MkplAppSettings) {
    val request = apiV2Mapper.decodeFromString<AdDeleteRequest>(receiveText())
    val context = MkplContext()
    context.fromTransport(request)
    process(context)
    respond(apiV2Mapper.encodeToString(context.toTransportDelete()))
}

suspend fun ApplicationCall.searchAd(appSettings: MkplAppSettings) {
    val request = apiV2Mapper.decodeFromString<AdSearchRequest>(receiveText())
    val context = MkplContext()
    context.fromTransport(request)
    process(context)
    respond(apiV2Mapper.encodeToString(context.toTransportSearch()))
}
