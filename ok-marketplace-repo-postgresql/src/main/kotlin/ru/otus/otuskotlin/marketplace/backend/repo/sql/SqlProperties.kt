package ru.otus.otuskotlin.marketplace.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/marketplacedevdb",
    val user: String = "postgres",
    val password: String = "marketplace-pass",
    val schema: String = "marketplace",
    val dropDatabase: Boolean = false,
    val fastMigration: Boolean = true,
)