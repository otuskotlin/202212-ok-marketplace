package ru.otus.otuskotlin.markeplace.springapp.common

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

fun MkplContext.withWorkModeBasedOnSettings(corSettings: MkplCorSettings): MkplContext {
    settings = corSettings
    workMode = when {
        (corSettings.repoTest != IAdRepository.NONE) -> MkplWorkMode.TEST
        (corSettings.repoStub != IAdRepository.NONE) -> MkplWorkMode.STUB
        else -> MkplWorkMode.PROD
    }
    return this
}
