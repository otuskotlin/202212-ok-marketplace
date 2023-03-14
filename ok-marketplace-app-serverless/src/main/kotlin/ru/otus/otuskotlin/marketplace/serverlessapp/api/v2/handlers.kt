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
import yandex.cloud.sdk.functions.Context

object CreateAdHandler : IV2HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("CreateAdHandler v2 start work")
        val request = req.toTransportModel<AdCreateRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportCreate().toResponse()
        }
    }
}

object ReadAdHandler : IV2HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("ReadAdHandler v2 start work")
        val request = req.toTransportModel<AdReadRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportCreate().toResponse()
        }
    }
}

object UpdateAdHandler : IV2HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("UpdateAdHandler v2 start work")
        val request = req.toTransportModel<AdUpdateRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportCreate().toResponse()
        }
    }
}

object DeleteAdHandler : IV2HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("DeleteAdHandler v2 start work")
        val request = req.toTransportModel<AdDeleteRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            toTransportCreate().toResponse()
        }
    }
}

object SearchAdHandler : IV2HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("SearchAdHandler v2 start work")
        val request = req.toTransportModel<AdSearchRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportCreate().toResponse()
        }
    }
}

object OffersAdHandler : IV2HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("OffersAdHandler v2 start work")
        val request = req.toTransportModel<AdOffersRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adsResponse.add(MkplAdStub.get())
            toTransportCreate().toResponse()
        }
    }
}