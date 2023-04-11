package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper

suspend fun ApplicationCall.offersAd(appSettings: MkplAppSettings, logger: IMpLogWrapper) =
    processV2<AdOffersRequest, AdOffersResponse>(appSettings, logger, "ad-offers", MkplCommand.OFFERS)
