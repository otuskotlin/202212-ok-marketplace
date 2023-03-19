package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

class MkplAdProcessor {
    suspend fun exec(ctx: MkplContext) {
        ctx.adResponse = MkplAdStub.get()
    }
}
