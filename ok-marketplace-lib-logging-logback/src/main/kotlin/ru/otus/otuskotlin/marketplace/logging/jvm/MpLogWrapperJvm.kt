package ru.otus.otuskotlin.marketplace.logging.jvm

import ch.qos.logback.classic.Logger
import net.logstash.logback.argument.StructuredArguments
import org.slf4j.Marker
import org.slf4j.event.KeyValuePair
import org.slf4j.event.Level
import org.slf4j.event.LoggingEvent
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper
import ru.otus.otuskotlin.marketplace.logging.common.LogLevel
import java.time.Instant

class MpLogWrapperJvm(
    /**
     * Экземпляр логера (Logback)
     */
    val logger: Logger,
    /**
     * Идентификатор логера. Пробрасывается в Logback и замещает loggerClass. Также используется в сообщения
     * логера о входе и выходе из функции.
     */
    override val loggerId: String = "",
) : IMpLogWrapper {
    /**
     * Основная функция для логирования
     */
    private fun log(
        msg: String = "",
        level: Level = Level.TRACE,
        marker: Marker = DefaultMarker("DEV"),
        e: Throwable? = null,
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) {
        logger.log(object : LoggingEvent {
            override fun getThrowable() = e
            override fun getTimeStamp(): Long = Instant.now().toEpochMilli()
            override fun getThreadName(): String = Thread.currentThread().name
            override fun getMessage(): String = msg
            override fun getArguments(): MutableList<Any> = argumentArray.toMutableList()
            override fun getArgumentArray(): Array<out Any> = data
                ?.let { d ->
                    listOfNotNull(
                        objs?.map { StructuredArguments.keyValue(it.key, it.value) }?.toTypedArray(),
                        StructuredArguments.keyValue("data", d),
                    ).toTypedArray()
                }
                ?: objs?.mapNotNull { StructuredArguments.keyValue(it.key, it.value) }?.toTypedArray()
                ?: emptyArray()

            override fun getMarkers(): MutableList<Marker> = mutableListOf(marker)
            override fun getKeyValuePairs(): MutableList<KeyValuePair> = objs
                ?.mapNotNull {
                    it.let { KeyValuePair(it.key, it.value) }
                }
                ?.toMutableList()
                ?: mutableListOf()

            override fun getLevel(): Level = level
            override fun getLoggerName(): String = logger.name
        })
    }

    override fun log(
        msg: String,
        level: LogLevel,
        marker: String,
        e: Throwable?,
        data: Any?,
        objs: Map<String, Any>?,
    ) = log(
        msg = msg,
        level = level.toSlf(),
        marker = DefaultMarker(marker),
        e = e,
        data = data,
        objs = objs,
    )

    private fun LogLevel.toSlf() = when (this) {
        LogLevel.ERROR -> Level.ERROR
        LogLevel.WARN -> Level.WARN
        LogLevel.INFO -> Level.INFO
        LogLevel.DEBUG -> Level.DEBUG
        LogLevel.TRACE -> Level.TRACE
    }
}
