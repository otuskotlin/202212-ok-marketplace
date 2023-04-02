package ru.otus.otuskotlin.markeplace.springapp.controllers.v2

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.markeplace.springapp.service.MkplAdBlockingProcessor
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportOffers

@RestController
@RequestMapping("v2/ad")
class OfferControllerV2(
    corSettings: MkplCorSettings,
    private val processor: MkplAdBlockingProcessor
) {

    @PostMapping("offers")
    suspend fun searchOffers(@RequestBody request: AdOffersRequest): AdOffersResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportOffers()
    }
}
