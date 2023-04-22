package ru.otus.otuskotlin.markeplace.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.otus.otuskotlin.markeplace.springapp.common.withWorkModeBasedOnSettings
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings

@Service
class MkplAdBlockingProcessor(private val mkplCorSettings: MkplCorSettings) {
    private val processor = MkplAdProcessor(settings = mkplCorSettings)

    fun exec(ctx: MkplContext) = runBlocking { processor.exec(ctx.withWorkModeBasedOnSettings(mkplCorSettings)) }
}