package ru.otus.otuskotlin.marketplace.serverlessapp.api.model

data class Response(
    val statusCode: Int,
    val isBase64Encoded: Boolean,
    val headers: Map<String, String>,
    val body: String
)