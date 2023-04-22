package ru.otus.otuskotlin.marketplace.app

import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

data class MkplAppSettings(
    val appUrls: List<String>,
    val corSettings: MkplCorSettings,
    val processor: MkplAdProcessor,
)

fun getAppSettingsWithRepo(repo: IAdRepository): MkplAppSettings {
    val corSettings = MkplCorSettings(repoTest = repo)
    return MkplAppSettings(
        appUrls = emptyList(),
        corSettings = corSettings,
        processor = MkplAdProcessor(corSettings)
    )
}
