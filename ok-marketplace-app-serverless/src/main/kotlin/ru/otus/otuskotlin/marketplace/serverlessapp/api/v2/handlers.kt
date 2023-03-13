package ru.otus.otuskotlin.marketplace.serverlessapp.api.v2

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toResponse
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toTransportModel
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportCreate
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.withContext
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import yandex.cloud.sdk.functions.YcFunction

val createAdHandler = YcFunction<Request, Response> { input, yandexContext ->
    println("CreateAdHandler v2 start work")
    val request = input.toTransportModel<AdCreateRequest>()
    withContext(yandexContext) {
        fromTransport(request)
        adResponse = MkplAdStub.get()
        toTransportCreate().toResponse()
    }
}

val readAdHandler = YcFunction<Request, Response> { input, yandexContext ->
    println("ReadAdHandler v2 start work")
    val request = input.toTransportModel<AdReadRequest>()
    withContext(yandexContext) {
        fromTransport(request)
        adResponse = MkplAdStub.get()
        toTransportCreate().toResponse()
    }
}

val updateAdHandler = YcFunction<Request, Response> { input, yandexContext ->
    println("UpdateAdHandler v2 start work")
    val request = input.toTransportModel<AdUpdateRequest>()
    withContext(yandexContext) {
        fromTransport(request)
        adResponse = MkplAdStub.get()
        toTransportCreate().toResponse()
    }
}

val deleteAdHandler = YcFunction<Request, Response> { input, yandexContext ->
    println("DeleteAdHandler v2 start work")
    val request = input.toTransportModel<AdDeleteRequest>()
    withContext(yandexContext) {
        fromTransport(request)
        toTransportCreate().toResponse()
    }
}

val searchAdHandler = YcFunction<Request, Response> { input, yandexContext ->
    println("SearchAdHandler v2 start work")
    val request = input.toTransportModel<AdSearchRequest>()
    withContext(yandexContext) {
        fromTransport(request)
        adResponse = MkplAdStub.get()
        toTransportCreate().toResponse()
    }
}

val offersAdHandler = YcFunction<Request, Response> { input, yandexContext ->
    println("OffersAdHandler v2 start work")
    val request = input.toTransportModel<AdOffersRequest>()
    withContext(yandexContext) {
        fromTransport(request)
        adsResponse.add(MkplAdStub.get())
        toTransportCreate().toResponse()
    }
}