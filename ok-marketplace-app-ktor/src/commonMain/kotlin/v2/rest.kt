@file:Suppress("DuplicatedCode")
package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings

fun Route.v2Ad(appSettings: MkplAppSettings) {
    val loggerAd = appSettings.corSettings.loggerProvider.logger(Route::v2Ad)
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

fun Route.v2Offer(appSettings: MkplAppSettings) {
    val loggerOffers = appSettings.corSettings.loggerProvider.logger(Route::v2Offer::class)
    route("ad") {
        post("offers") {
            call.offersAd(appSettings, loggerOffers)
        }
    }
}
