@file:Suppress("DuplicatedCode")
package ru.otus.otuskotlin.marketplace.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor

fun Route.v1Ad(processor: MkplAdProcessor, appSettings: MkplAppSettings) {
    val loggerAd = appSettings.corSettings.loggerProvider.logger(Route::v1Ad::class)
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

fun Route.v1Offer(processor: MkplAdProcessor, appSettings: MkplAppSettings) {
    val loggerOffers = appSettings.corSettings.loggerProvider.logger(Route::v1Offer::class)
    route("ad") {
        post("offers") {
            call.offersAd(processor, loggerOffers)
        }
    }
}
