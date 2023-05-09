package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

expect fun Application.getDatabaseConf(type: AdDbType): IAdRepository

enum class AdDbType(val confName: String) {
    PROD("prod"), TEST("test")
}
