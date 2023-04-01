package ru.otus.otuskotlin.marketplace.app.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.logs.mapper.toLog
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

private val clazzCreate = ApplicationCall::createAd::class.qualifiedName ?: "create"
suspend fun ApplicationCall.createAd(appSettings: MkplAppSettings) {
    val logId = "create"
    val logger = appSettings.corSettings.loggerProvider.logger(clazzCreate)

    logger.doWithLogging(logId) {
        val request = receive<AdCreateRequest>()
        val context = MkplContext()
        context.fromTransport(request)
        logger.info(
            msg = "${context.command} request is got",
            data = context.toLog("${logId}-request"),
        )
        context.adResponse = MkplAdStub.get()
        respond(context.toTransportCreate())
        logger.info(
            msg = "${context.command} response is sent",
            data = context.toLog("${logId}-response")
        )
    }
}

suspend fun ApplicationCall.readAd(appSettings: MkplAppSettings) {
    val request = receive<AdReadRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAd(appSettings: MkplAppSettings) {
    val request = receive<AdUpdateRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteAd(appSettings: MkplAppSettings) {
    val request = receive<AdDeleteRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchAd(appSettings: MkplAppSettings) {
    val request = receive<AdSearchRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adsResponse.addAll(MkplAdStub.prepareSearchList("Болт", MkplDealSide.DEMAND))
    respond(context.toTransportSearch())
}
