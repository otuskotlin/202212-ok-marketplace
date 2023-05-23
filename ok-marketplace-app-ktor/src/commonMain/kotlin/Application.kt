package ru.otus.otuskotlin.marketplace.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.plugins.initAppSettings
import ru.otus.otuskotlin.marketplace.app.plugins.initPlugins
import ru.otus.otuskotlin.marketplace.app.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.v2.v2Offer
import ru.otus.otuskotlin.marketplace.app.v2.wsHandlerV2

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(
    appSettings: MkplAppSettings = initAppSettings(),
    authConfig: KtorAuthConfig = KtorAuthConfig(environment)
) {
    initPlugins(appSettings)
    routing {
        route("v2") {
            v2Ad(appSettings = appSettings)
            v2Offer(appSettings = appSettings)
            webSocket("/ws") {
                wsHandlerV2(appSettings)
            }
        }
    }
}
