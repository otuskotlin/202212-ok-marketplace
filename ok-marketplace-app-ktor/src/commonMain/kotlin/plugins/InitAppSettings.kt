package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.AdRepoStub
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory

fun Application.initAppSettings(): MkplAppSettings = MkplAppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    corSettings = MkplCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = AdRepoStub(),
        repoProd = AdRepoInMemory(),
        repoStub = AdRepoStub(),
    ),
    processor = MkplAdProcessor(),
)

expect fun Application.getLoggerProviderConf(): MpLoggerProvider
