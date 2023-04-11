@file:Suppress("DuplicatedCode")
package ru.otus.otuskotlin.marketplace.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings

fun Route.v1Ad(appSettings: MkplAppSettings) {
    val loggerAd = appSettings.corSettings.loggerProvider.logger(Route::v1Ad::class)
    route("ad") {
        post("create") {
            call.createAd(appSettings, loggerAd)
        }
        post("read") {
            call.readAd(appSettings, loggerAd)
        }
        post("update") {
            call.updateAd(appSettings, loggerAd)
        }
        post("delete") {
            call.deleteAd(appSettings, loggerAd)
        }
        post("search") {
            call.searchAd(appSettings, loggerAd)
        }
    }
}

fun Route.v1Offer(appSettings: MkplAppSettings) {
    val loggerOffers = appSettings.corSettings.loggerProvider.logger(Route::v1Offer::class)
    route("ad") {
        post("offers") {
            call.offersAd(appSettings, loggerOffers)
        }
    }
}
