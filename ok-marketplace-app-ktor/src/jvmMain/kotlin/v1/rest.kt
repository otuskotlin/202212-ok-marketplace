@file:Suppress("DuplicatedCode")
package ru.otus.otuskotlin.marketplace.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings

fun Route.v1Ad(appSettings: MkplAppSettings) {
    route("ad") {
        post("create") {
            call.createAd(appSettings)
        }
        post("read") {
            call.readAd(appSettings)
        }
        post("update") {
            call.updateAd(appSettings)
        }
        post("delete") {
            call.deleteAd(appSettings)
        }
        post("search") {
            call.searchAd(appSettings)
        }
    }
}

fun Route.v1Offer(appSettings: MkplAppSettings) {
    route("ad") {
        post("offers") {
            call.offersAd(appSettings)
        }
    }
}
