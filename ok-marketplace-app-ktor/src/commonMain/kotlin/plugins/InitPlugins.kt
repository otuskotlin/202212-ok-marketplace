package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings

fun Application.initPlugins(appSettings: MkplAppSettings) {
    install(Routing)
    install(WebSockets)

    install(CORS) {
        allowNonSimpleContentTypes = true
        allowSameOrigin = true
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader("*")
        appSettings.appUrls.forEach {
            val split = it.split("://")
            println("$split")
            when (split.size) {
                2 -> allowHost(
                    split[1].split("/")[0]/*.apply { log(module = "app", msg = "COR: $this") }*/,
                    listOf(split[0])
                )

                1 -> allowHost(
                    split[0].split("/")[0]/*.apply { log(module = "app", msg = "COR: $this") }*/,
                    listOf("http", "https")
                )
            }
        }
    }
    install(CachingHeaders)
    install(AutoHeadResponse)

//    install(ContentNegotiation) {
//        json(apiV2Mapper)
//    }
}
