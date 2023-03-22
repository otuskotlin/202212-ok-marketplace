package ru.otus.otuskotlin.marketplace.app.rabbit.controller

import kotlinx.coroutines.*
import ru.otus.otuskotlin.marketplace.app.rabbit.RabbitProcessorBase

// TODO-rmq-5: запуск процессора
class RabbitController(
    private val processors: Set<RabbitProcessorBase>
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val limitedParallelismContext = Dispatchers.IO.limitedParallelism(1)

    private val scope = CoroutineScope(
        limitedParallelismContext + CoroutineName("thread-rabbitmq-controller")
    )


    fun start() = scope.launch {
        processors.forEach {
            launch(
                limitedParallelismContext + CoroutineName("thread-${it.processorConfig.consumerTag}")
            ) {
                try {
                    it.process()
                } catch (e: RuntimeException) {
                    // логируем, что-то делаем
                    e.printStackTrace()
                }
            }
        }
    }
}
