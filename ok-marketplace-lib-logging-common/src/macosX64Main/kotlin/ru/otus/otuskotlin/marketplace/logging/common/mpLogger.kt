package ru.otus.otuskotlin.marketplace.logging.common

import ru.otus.otuskotlin.marketplace.logging.impl.mpLoggerCommon
import kotlin.reflect.KClass

@Suppress("unused")
actual fun mpLogger(loggerId: String) = mpLoggerCommon(loggerId)

actual fun mpLogger(cls: KClass<*>) = mpLoggerCommon(cls)