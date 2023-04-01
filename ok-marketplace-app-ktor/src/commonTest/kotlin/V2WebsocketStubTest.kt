package ru.otus.otuskotlin.marketplace.app

import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class V2WebsocketStubTest {

    @Test
    fun createStub() {
        val request = AdCreateRequest(
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

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun readStub() {
        val request = AdReadRequest(
            requestId = "12345",
            ad = AdReadObject("666"),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val request = AdUpdateRequest(
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

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun deleteStub() {
        val request = AdDeleteRequest(
            requestId = "12345",
            ad = AdDeleteObject(
                id = "666",
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun searchStub() {
        val request = AdSearchRequest(
            requestId = "12345",
            adFilter = AdSearchFilter(),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun offersStub() {
        val request = AdOffersRequest(
            requestId = "12345",
            ad = AdReadObject(
                id = "666",
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private inline fun <reified T> testMethod(
        request: IRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        // TODO testApplication не поддерживает native websocket
//        application { module() }
//        val client = createClient {
//            install(WebSockets)
//        }
//
//        client.webSocket("/v2/ws") {
//            withTimeout(3000) {
//                val incame = incoming.receive() as Frame.Text
//                val response = apiV2Mapper.decodeFromString<T>(incame.readText())
//                assertIs<AdInitResponse>(response)
//            }
//            send(Frame.Text(apiV2Mapper.encodeToString(request)))
//            withTimeout(3000) {
//                val incame = incoming.receive() as Frame.Text
//                val text = incame.readText()
//                val response = apiV2Mapper.decodeFromString<T>(text)
//
//                assertBlock(response)
//            }
//        }
    }
}
