package ru.otus.otuskotlin.marketplace.app.rabbit.biz

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

//  TODO-rmq-7: Это класс - заглушка, после вебинара по бизнес-логике от него можно будет избавиться
class MkplAdProcessor {

    fun exec(ctx: MkplContext) {
        ctx.adResponse = MkplAdStub.get()
    }
}
