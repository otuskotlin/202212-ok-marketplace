package ru.otus.otuskotlin.marketplace.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/marketplace",
    val user: String = "postgres",
    val password: String = "marketplace-pass",
    val schema: String = "marketplace",
    // Удалять таблицы при старте - нужно для тестирования
    val dropDatabase: Boolean = false,
)