package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.logs.mapper.toLog
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserGroups
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd

// TODO-validation-1: смотрим универсальную функцию обработки запроса (v2)
suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2(
    appSettings: MkplAppSettings,
    logger: IMpLogWrapper,
    logId: String,
    command: MkplCommand? = null,
) {
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
    )
    val processor = appSettings.processor
    try {
        logger.doWithLogging(id = logId) {
            ctx.principal = mkplPrincipal(appSettings)
            val request = apiV2Mapper.decodeFromString<Q>(receiveText())
            ctx.fromTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(apiV2Mapper.encodeToString(ctx.toTransportAd()))
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = MkplState.FAILING
            ctx.errors.add(e.asMkplError())
            processor.exec(ctx)
            respond(apiV2Mapper.encodeToString(ctx.toTransportAd()))
        }
    }
}

// TODO: костыль для решения проблемы отсутствия jwt в native
@Suppress("UnusedReceiverParameter", "UNUSED_PARAMETER")
fun ApplicationCall.mkplPrincipal(appSettings: MkplAppSettings): MkplPrincipalModel = MkplPrincipalModel(
    id = MkplUserId("user-1"),
    fname = "Ivan",
    mname = "Ivanovich",
    lname = "Ivanov",
    groups = setOf(MkplUserGroups.TEST, MkplUserGroups.USER),
)
