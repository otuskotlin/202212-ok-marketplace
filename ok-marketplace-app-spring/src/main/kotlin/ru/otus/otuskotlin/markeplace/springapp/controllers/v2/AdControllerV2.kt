package ru.otus.otuskotlin.markeplace.springapp.controllers.v2

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider
import ru.otus.otuskotlin.marketplace.mappers.v2.*

@RestController
@RequestMapping("v2/ad")
class AdControllerV2(
    private val processor: MkplAdProcessor,
    private val loggerProvider: MpLoggerProvider,
) {

    @PostMapping("create")
    suspend fun createAd(@RequestBody request: String): String =
        processV2<AdCreateRequest, AdCreateResponse>(processor, MkplCommand.CREATE, requestString = request, loggerProvider.logger(AdControllerV2::class), "ad-create")

    @PostMapping("read")
    suspend fun readAd(@RequestBody request: String): String =
        processV2<AdCreateRequest, AdCreateResponse>(processor, MkplCommand.CREATE, requestString = request, loggerProvider.logger(AdControllerV2::class), "ad-create")

    @PostMapping("update")
    suspend fun updateAd(@RequestBody request: String): String =
        processV2<AdUpdateRequest, AdUpdateResponse>(processor, MkplCommand.UPDATE, requestString = request, loggerProvider.logger(AdControllerV2::class), "ad-update")

    @PostMapping("delete")
    suspend fun deleteAd(@RequestBody request: String): String =
        processV2<AdDeleteRequest, AdDeleteResponse>(processor, MkplCommand.DELETE, requestString = request, loggerProvider.logger(AdControllerV2::class), "ad-delete")

    @PostMapping("search")
    suspend fun searchAd(@RequestBody request: String): String =
        processV2<AdSearchRequest, AdSearchResponse>(processor, MkplCommand.SEARCH, requestString = request, loggerProvider.logger(AdControllerV2::class), "ad-search")
}
