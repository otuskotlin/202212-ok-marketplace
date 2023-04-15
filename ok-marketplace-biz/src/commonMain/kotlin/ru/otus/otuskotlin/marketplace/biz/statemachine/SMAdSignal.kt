package ru.otus.otuskotlin.marketplace.biz.statemachine

import ru.otus.otuskotlin.marketplace.common.statemachine.SMAdStates
import kotlin.time.Duration

data class SMAdSignal(
    val state: SMAdStates,
    val duration: Duration,
    val views: Int,
)
