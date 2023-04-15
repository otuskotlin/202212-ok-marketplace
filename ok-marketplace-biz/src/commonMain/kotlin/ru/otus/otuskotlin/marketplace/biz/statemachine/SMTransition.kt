package ru.otus.otuskotlin.marketplace.biz.statemachine

import ru.otus.otuskotlin.marketplace.common.statemachine.SMAdStates

data class SMTransition(
    val state: SMAdStates,
    val description: String,
)
