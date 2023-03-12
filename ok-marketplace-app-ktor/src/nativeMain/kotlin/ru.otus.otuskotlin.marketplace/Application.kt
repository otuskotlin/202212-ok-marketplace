package ru.otus.otuskotlin.marketplace

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.marketplace.app.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.v2.v2Offer

fun main() {
    embeddedServer(CIO, port = 8080) {
        install(Routing)
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }

        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
            route("v2") {
                v2Ad()
                v2Offer()
            }
        }
    }.start(wait = true)
}
