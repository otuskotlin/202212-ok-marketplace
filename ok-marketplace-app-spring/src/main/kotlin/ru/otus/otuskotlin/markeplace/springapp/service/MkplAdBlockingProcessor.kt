package ru.otus.otuskotlin.markeplace.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext

@Service
class MkplAdBlockingProcessor {
    private val processor = MkplAdProcessor()

    fun exec(ctx: MkplContext) = runBlocking { processor.exec(ctx) }
}