package ru.otus.otuskotlin.marketplace.app

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.helpers.testSettings
import ru.otus.otuskotlin.marketplace.app.ru.otus.otuskotlin.marketplace.auth.addAuth
import kotlin.test.Test
import kotlin.test.assertEquals

class V2AdStubApiTest {

    @Test
    fun create() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v2/ad/create") {
            val requestObj = AdCreateRequest(
                requestId = "12345",
                ad = AdCreateObject(
                    title = "Болт",
                    description = "КРУТЕЙШИЙ",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = KtorAuthConfig.TEST)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun read() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v2/ad/read") {
            val requestObj = AdReadRequest(
                requestId = "12345",
                ad = AdReadObject("666"),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = KtorAuthConfig.TEST)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun update() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v2/ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "12345",
                ad = AdUpdateObject(
                    id = "666",
                    title = "Болт",
                    description = "КРУТЕЙШИЙ",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = KtorAuthConfig.TEST)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun delete() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v2/ad/delete") {
            val requestObj = AdDeleteRequest(
                requestId = "12345",
                ad = AdDeleteObject(
                    id = "666",
                    lock = "123"
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = KtorAuthConfig.TEST)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun search() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v2/ad/search") {
            val requestObj = AdSearchRequest(
                requestId = "12345",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = KtorAuthConfig.TEST)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.ads?.first()?.id)
    }

    @Test
    fun offers() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v2/ad/offers") {
            val requestObj = AdOffersRequest(
                requestId = "12345",
                ad = AdReadObject(
                    id = "666"
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            addAuth(config = KtorAuthConfig.TEST)
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdOffersResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("s-666-01", responseObj.ads?.first()?.id)
    }
}
