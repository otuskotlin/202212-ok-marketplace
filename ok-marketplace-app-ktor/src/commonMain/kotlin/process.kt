package ru.otus.otuskotlin.marketplace.app

import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext

private val processor = MkplAdProcessor()
suspend fun process(ctx: MkplContext) = processor.exec(ctx)

