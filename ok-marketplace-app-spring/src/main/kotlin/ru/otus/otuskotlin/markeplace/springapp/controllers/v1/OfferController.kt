package ru.otus.otuskotlin.markeplace.springapp.controllers.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider

@RestController
@RequestMapping("v1/ad")
class OfferController(
    private val processor: MkplAdProcessor,
    private val loggerProvider: MpLoggerProvider,
) {

    @PostMapping("offers")
    suspend fun searchOffers(@RequestBody request: AdOffersRequest): AdOffersResponse =
        processV1(processor, MkplCommand.OFFERS, request = request, loggerProvider.logger(OfferController::class), "ad-offers")

}
