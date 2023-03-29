package ru.otus.otuskotlin.markeplace.springapp.api.v1.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

// Temporary simple test with stubs
@WebFluxTest(AdController::class, OfferController::class)
internal class AdControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun createAd() = testStubAd(
        "/v1/ad/create",
        AdCreateRequest(),
        MkplContext().apply { adResponse = MkplAdStub.get() }.toTransportCreate()
    )

    @Test
    fun readAd() = testStubAd(
        "/v1/ad/read",
        AdReadRequest(),
        MkplContext().apply { adResponse = MkplAdStub.get() }.toTransportRead()
    )

    @Test
    fun updateAd() = testStubAd(
        "/v1/ad/update",
        AdUpdateRequest(),
        MkplContext().apply { adResponse = MkplAdStub.get() }.toTransportUpdate()
    )

    @Test
    fun deleteAd() = testStubAd(
        "/v1/ad/delete",
        AdDeleteRequest(),
        MkplContext().toTransportDelete()
    )

    @Test
    fun searchAd() = testStubAd(
        "/v1/ad/search",
        AdSearchRequest(),
        MkplContext().apply { adsResponse.add(MkplAdStub.get()) }.toTransportSearch()
    )

    @Test
    fun searchOffers() = testStubAd(
        "/v1/ad/offers",
        AdOffersRequest(),
        MkplContext().apply { adsResponse.add(MkplAdStub.get()) }.toTransportOffers()
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubAd(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}
