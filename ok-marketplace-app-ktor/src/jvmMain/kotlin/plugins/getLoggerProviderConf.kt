package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider
import ru.otus.otuskotlin.marketplace.logging.jvm.mpLoggerLogback
import ru.otus.otuskotlin.marketplace.logging.kermit.mpLoggerKermit

actual fun Application.getLoggerProviderConf(): MpLoggerProvider =
    when (environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "mp" -> MpLoggerProvider { mpLoggerKermit(it) }
        else -> MpLoggerProvider { mpLoggerLogback(it) }
}
