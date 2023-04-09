@file:Suppress("DuplicatedCode")
package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings

fun Route.v2Ad(appSettings: MkplAppSettings) {
    val loggerAd = appSettings.corSettings.loggerProvider.logger(Route::v2Ad::class)
    val processor = appSettings.processor
    route("ad") {
        post("create") {
            call.createAd(processor, loggerAd)
        }
        post("read") {
            call.readAd(processor, loggerAd)
        }
        post("update") {
            call.updateAd(processor, loggerAd)
        }
        post("delete") {
            call.deleteAd(processor, loggerAd)
        }
        post("search") {
            call.searchAd(processor, loggerAd)
        }
    }
}

fun Route.v2Offer(appSettings: MkplAppSettings) {
    val loggerOffers = appSettings.corSettings.loggerProvider.logger(Route::v2Offer::class)
    val processor = appSettings.processor
    route("ad") {
        post("offers") {
            call.offersAd(processor, loggerOffers)
        }
    }
}
