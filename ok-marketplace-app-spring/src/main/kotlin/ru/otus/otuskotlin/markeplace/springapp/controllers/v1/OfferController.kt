package ru.otus.otuskotlin.markeplace.springapp.controllers.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.markeplace.springapp.service.MkplAdBlockingProcessor
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportOffers

@RestController
@RequestMapping("v1/ad")
class OfferController(
    corSettings: MkplCorSettings,
    private val processor: MkplAdBlockingProcessor
) {

    @PostMapping("offers")
    fun searchOffers(@RequestBody request: AdOffersRequest): AdOffersResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportOffers()
    }
}
