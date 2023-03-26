package ru.otus.otuskotlin.marketplace.logging.common

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@Suppress("unused")
interface IMpLogWrapper {
    val loggerId: String

    fun log(
        msg: String = "",
        level: LogLevel = LogLevel.TRACE,
        marker: String = "DEV",
        e: Throwable? = null,
        data: Any? = null,
        objs: Map<String, Any>? = null,
    )

    fun error(
        msg: String = "",
        marker: String = "DEV",
        e: Throwable? = null,
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) = log(msg, LogLevel.ERROR, marker, e, data, objs)

    fun info(
        msg: String = "",
        marker: String = "DEV",
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) = log(msg, LogLevel.INFO, marker, null, data, objs)

    fun debug(
        msg: String = "",
        marker: String = "DEV",
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) = log(msg, LogLevel.DEBUG, marker, null, data, objs)

    /**
     * Функция обертка для выполнения прикладного кода с логированием перед выполнением и после
     */
    @OptIn(ExperimentalTime::class)
    suspend fun <T> doWithLogging(
        id: String = "",
        level: LogLevel = LogLevel.INFO,
        block: suspend () -> T,
    ): T = try {
        log("Started $loggerId $id", level)
        val (res, diffTime) = measureTimedValue { block() }

        log(
            msg = "Finished $loggerId $id",
            level = level,
            objs = mapOf("metricHandleTime" to diffTime.toIsoString())
        )
        res
    } catch (e: Throwable) {
        log(
            msg = "Failed $loggerId $id",
            level = LogLevel.ERROR,
            e = e
        )
        throw e
    }

    /**
     * Функция обертка для выполнения прикладного кода с логированием ошибки
     */
    suspend fun <T> doWithErrorLogging(
        id: String = "",
        throwRequired: Boolean = true,
        block: suspend () -> T,
    ): T? = try {
        val result = block()
        result
    } catch (e: Throwable) {
        log(
            msg = "Failed $loggerId $id",
            level = LogLevel.ERROR,
            e = e
        )
        if (throwRequired) throw e else null
    }
}
