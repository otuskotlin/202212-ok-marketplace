package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory

actual fun Application.getDatabaseConf(type: AdDbType): IAdRepository {
    return AdRepoInMemory()
}
