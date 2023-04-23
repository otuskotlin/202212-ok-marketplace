package ru.otus.otuskotlin.marketplace.app

import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings

data class MkplAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: MkplCorSettings,
    val processor: MkplAdProcessor = MkplAdProcessor(settings = corSettings),
)
