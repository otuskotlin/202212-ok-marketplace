package ru.otus.otuskotlin.markeplace.springapp.controllers.v1

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.markeplace.springapp.service.MkplAdBlockingProcessor
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider
import ru.otus.otuskotlin.marketplace.mappers.v1.*

@RestController
@RequestMapping("v1/ad")
class AdController(
    private val processor: MkplAdBlockingProcessor,
    private val loggerProvider: MpLoggerProvider,
) {

    @PostMapping("create")
    suspend fun createAd(@RequestBody request: AdCreateRequest): AdCreateResponse =
        processV1(processor, MkplCommand.CREATE, request = request, loggerProvider.logger(AdController::class), "ad-create")

    @PostMapping("read")
    suspend fun  readAd(@RequestBody request: AdReadRequest): AdReadResponse =
        processV1(processor, MkplCommand.READ, request = request, loggerProvider.logger(AdController::class), "ad-read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  updateAd(@RequestBody request: AdUpdateRequest): AdUpdateResponse =
        processV1(processor, MkplCommand.UPDATE, request = request, loggerProvider.logger(AdController::class), "ad-update")

    @PostMapping("delete")
    suspend fun  deleteAd(@RequestBody request: AdDeleteRequest): AdDeleteResponse =
        processV1(processor, MkplCommand.DELETE, request = request, loggerProvider.logger(AdController::class), "ad-delete")

    @PostMapping("search")
    suspend fun  searchAd(@RequestBody request: AdSearchRequest): AdSearchResponse =
        processV1(processor, MkplCommand.SEARCH, request = request, loggerProvider.logger(AdController::class), "ad-search")
}
