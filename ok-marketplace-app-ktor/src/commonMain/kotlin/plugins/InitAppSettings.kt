package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.app.MkplAppSettings
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.AdRepoStub
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings

fun Application.initAppSettings(): MkplAppSettings {
    val corSettings = MkplCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = getDatabaseConf(AdDbType.TEST),
        repoProd = getDatabaseConf(AdDbType.PROD),
        repoStub = AdRepoStub(),
    )
    return MkplAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = MkplAdProcessor(corSettings),
    )
}
