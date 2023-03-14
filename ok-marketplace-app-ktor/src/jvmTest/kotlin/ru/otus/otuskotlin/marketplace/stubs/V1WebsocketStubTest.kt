package ru.otus.otuskotlin.marketplace.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WebsocketStubTest {

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

    private inline fun <reified T> testMethod(
        request: Any,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v1") {
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val response = apiV1Mapper.readValue(incame.readText(), T::class.java)
                assertIs<AdInitResponse>(response)
            }
            send(Frame.Text(apiV1Mapper.writeValueAsString(request)))
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val response = apiV1Mapper.readValue(incame.readText(), T::class.java)

                assertBlock(response)
            }
        }
    }
}
