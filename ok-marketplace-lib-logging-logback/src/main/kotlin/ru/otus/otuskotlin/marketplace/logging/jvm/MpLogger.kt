package ru.otus.otuskotlin.marketplace.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mpLoggerJvm(logger: Logger): IMpLogWrapper = MpLogWrapperJvm(
    logger = logger,
    loggerId = logger.name,
)

fun mpLoggerJvm(clazz: KClass<*>): IMpLogWrapper = mpLoggerJvm(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun mpLoggerJvm(loggerId: String): IMpLogWrapper = mpLoggerJvm(LoggerFactory.getLogger(loggerId) as Logger)
