package ru.otus.otuskotlin.marketplace.app

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.app.plugins.initAppSettings
import ru.otus.otuskotlin.marketplace.app.plugins.initPlugins
import ru.otus.otuskotlin.marketplace.app.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.v2.v2Offer
import ru.otus.otuskotlin.marketplace.app.v2.wsHandlerV2
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(appSettings: MkplAppSettings = initAppSettings()) {
    initPlugins(appSettings)
    val processor = MkplAdProcessor()
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v2") {
            v2Ad(processor = processor)
            v2Offer(processor = processor)
            webSocket("/ws") {
                wsHandlerV2(appSettings)
            }
        }

    }
}
