package ru.otus.otuskotlin.markeplace.springapp.controllers.v2

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.markeplace.springapp.service.MkplAdBlockingProcessor
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.mappers.v2.*

@RestController
@RequestMapping("v2/ad")
class AdControllerV2(
    corSettings: MkplCorSettings,
    private val processor: MkplAdBlockingProcessor
) {

    @PostMapping("create")
    suspend fun createAd(@RequestBody request: AdCreateRequest): AdCreateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    suspend fun readAd(@RequestBody request: AdReadRequest): AdReadResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @PostMapping("update")
    suspend fun updateAd(@RequestBody request: AdUpdateRequest): AdUpdateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    suspend fun deleteAd(@RequestBody request: AdDeleteRequest): AdDeleteResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    suspend fun searchAd(@RequestBody request: AdSearchRequest): AdSearchResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}
