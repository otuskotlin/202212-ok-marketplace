package ru.otus.otuskotlin.marketplace.app.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper

suspend fun ApplicationCall.createAd(appSettings: MkplAppSettings, logger: IMpLogWrapper) =
    processV1<AdCreateRequest, AdCreateResponse>(appSettings, logger, "ad-create", MkplCommand.CREATE)

suspend fun ApplicationCall.readAd(appSettings: MkplAppSettings, logger: IMpLogWrapper) =
    processV1<AdReadRequest, AdReadResponse>(appSettings, logger, "ad-read", MkplCommand.READ)

suspend fun ApplicationCall.updateAd(appSettings: MkplAppSettings, logger: IMpLogWrapper) =
    processV1<AdUpdateRequest, AdUpdateResponse>(appSettings, logger, "ad-update", MkplCommand.UPDATE)

suspend fun ApplicationCall.deleteAd(appSettings: MkplAppSettings, logger: IMpLogWrapper) =
    processV1<AdDeleteRequest, AdDeleteResponse>(appSettings, logger, "ad-delete", MkplCommand.DELETE)

suspend fun ApplicationCall.searchAd(appSettings: MkplAppSettings,logger: IMpLogWrapper) =
    processV1<AdSearchRequest, AdSearchResponse>(appSettings, logger, "ad-search", MkplCommand.SEARCH)
