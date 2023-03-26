package ru.otus.otuskotlin.marketplace.app.rabbit

import ru.otus.otuskotlin.marketplace.app.rabbit.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.marketplace.app.rabbit.controller.RabbitController
import ru.otus.otuskotlin.marketplace.app.rabbit.processor.RabbitDirectProcessorV1
import ru.otus.otuskotlin.marketplace.app.rabbit.processor.RabbitDirectProcessorV2
//import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor

// TODO-rmq-2: смотрим настройки приложения
fun main() {
    val config = RabbitConfig()
    val adProcessor = MkplAdProcessor()

    // TODO-rmq-4: т.к. у нас две версии API, создаём две конфигурации,
    //  передаем их в два процессора и объединяем в рамках контроллера

    val producerConfigV1 = RabbitExchangeConfiguration(
        keyIn = "in-v1",
        keyOut = "out-v1",
        exchange = "transport-exchange",
        queue = "v1-queue",
        consumerTag = "v1-consumer",
        exchangeType = "direct"
    )

    val producerConfigV2 = RabbitExchangeConfiguration(
        keyIn = "in-v2",
        keyOut = "out-v2",
        exchange = "transport-exchange",
        queue = "v2-queue",
        consumerTag = "v2-consumer",
        exchangeType = "direct"
    )

    val processor by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = producerConfigV1,
            processor = adProcessor
        )
    }

    val processor2 by lazy {
        RabbitDirectProcessorV2(
            config = config,
            processorConfig = producerConfigV2,
            processor = adProcessor
        )
    }
    val controller by lazy {
        RabbitController(
            processors = setOf(processor, processor2)
        )
    }
    controller.start()
}
