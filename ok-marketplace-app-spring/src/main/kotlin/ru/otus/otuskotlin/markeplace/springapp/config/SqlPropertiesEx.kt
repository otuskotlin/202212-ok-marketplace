package ru.otus.otuskotlin.markeplace.springapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import ru.otus.otuskotlin.marketplace.backend.repo.sql.SqlProperties

// Нужна аннотация @ConstructorBinding, ее нельзя поставить над методом c @Bean, не нашел другого пути
@ConfigurationProperties("sql")
class SqlPropertiesEx
    @ConstructorBinding
    constructor(
        url: String,
        user: String,
        password: String,
        schema: String,
        dropDatabase: Boolean
    ): SqlProperties(url, user, password, schema, dropDatabase)