package ru.otus.otuskotlin.marketplace.biz.statemachine

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.NONE
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.statemachine.SMAdStates
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import kotlin.reflect.KClass

private val machine = SMAdStateResolver()
private val clazz: KClass<*> = ICorChainDsl<MkplContext>::computeAdState::class

fun ICorChainDsl<MkplContext>.computeAdState(title: String) = worker {
    this.title = title
    this.description = "Вычисление состояния объявления"
    on { state == MkplState.RUNNING }
    handle {
        val log = settings.loggerProvider.logger(clazz)
        val timeNow = Clock.System.now()
        val ad = adValidated
        val prevState = ad.adState
        val timePublished = ad.timePublished.takeIf { it != Instant.NONE } ?: timeNow
        val signal = SMAdSignal(
            state = prevState.takeIf { it != SMAdStates.NONE } ?: SMAdStates.NEW,
            duration = timeNow - timePublished,
            views = ad.views,
        )
        val transition = machine.resolve(signal)
        if (transition.state != prevState) {
            log.info("New ad state transition: ${transition.description}")
        }
        ad.adState = transition.state
    }
}
