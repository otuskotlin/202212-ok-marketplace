package ru.otus.otuskotlin.markeplace.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory

@Configuration
class TestConfig {

    @Bean
    @Primary
    fun testCorSettings(loggerProvider: MpLoggerProvider): MkplCorSettings = MkplCorSettings(
        loggerProvider = loggerProvider,
        repoTest = AdRepoInMemory()
    )

}