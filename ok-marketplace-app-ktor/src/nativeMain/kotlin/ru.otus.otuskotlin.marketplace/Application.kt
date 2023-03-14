package ru.otus.otuskotlin.marketplace

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.app.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.v2.v2Offer
import ru.otus.otuskotlin.marketplace.app.v2.wsHandlerV2

fun Application.module() {
    install(Routing)
    install(WebSockets)

    install(ContentNegotiation) {
        json(apiV2Mapper)
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("v2") {
            v2Ad()
            v2Offer()
        }
        webSocket("/ws/v2") {
            wsHandlerV2()
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080) {
        module()
    } .start(wait = true)
}
