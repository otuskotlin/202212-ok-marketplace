package ru.otus.otuskotlin.marketplace.serverlessapp.api.v1

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toResponse
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toTransportModel
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.withContext
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import yandex.cloud.sdk.functions.Context

object CreateAdHandler : IV1HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("CreateAdHandler v1 start work")
        val request = req.toTransportModel<AdCreateRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportCreate().toResponse()
        }
    }
}

object ReadAdHandler : IV1HandleStrategy {
    override val path: String = "ad/read"
    override fun handle(req: Request, reqContext: Context): Response {
        println("ReadAdHandler v1 start work")
        val request = req.toTransportModel<AdReadRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportRead().toResponse()
        }
    }
}

object UpdateAdHandler : IV1HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {

        println("UpdateAdHandler v1 start work")
        val request = req.toTransportModel<AdUpdateRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportUpdate().toResponse()
        }

    }
}

object DeleteAdHandler : IV1HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("DeleteAdHandler v1 start work")
        val request = req.toTransportModel<AdDeleteRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            toTransportDelete().toResponse()
        }
    }
}

object SearchAdHandler : IV1HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("SearchAdHandler v1 start work")
        val request = req.toTransportModel<AdSearchRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adResponse = MkplAdStub.get()
            toTransportSearch().toResponse()
        }
    }
}

object OffersAdHandler : IV1HandleStrategy {
    override val path: String = "ad/create"
    override fun handle(req: Request, reqContext: Context): Response {
        println("OffersAdHandler v1 start work")
        val request = req.toTransportModel<AdOffersRequest>()
        return withContext(reqContext) {
            fromTransport(request)
            adsResponse.add(MkplAdStub.get())
            toTransportOffers().toResponse()
        }
    }
}