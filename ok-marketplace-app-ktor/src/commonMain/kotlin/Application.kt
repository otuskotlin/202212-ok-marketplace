package ru.otus.otuskotlin.marketplace.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.app.plugins.initAppSettings
import ru.otus.otuskotlin.marketplace.app.plugins.initPlugins
import ru.otus.otuskotlin.marketplace.app.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.v2.v2Offer
import ru.otus.otuskotlin.marketplace.app.v2.wsHandlerV2

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(appSettings: MkplAppSettings = initAppSettings()) {
    initPlugins(appSettings)

    routing {
//        get("/") {
//            call.respondText("Hello, world!")
//        }

        route("v2") {
            v2Ad(appSettings)
            v2Offer(appSettings)
            webSocket("/ws") {
                wsHandlerV2(appSettings)
            }
        }

    }
}
