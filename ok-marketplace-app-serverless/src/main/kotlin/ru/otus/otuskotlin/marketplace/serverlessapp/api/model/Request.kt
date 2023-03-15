package ru.otus.otuskotlin.marketplace.serverlessapp.api.model

data class Request(
    var httpMethod: String? = null,
    var headers: Map<String, String>? = null,
    var url: String? = null,
    var body: String? = null,
    var path: String? = null,
    var params: Map<String, String>? = null,
    var multiValueParams: Map<String, List<String>>? = null,
    var pathParams: Map<String, String>? = null,
    var version: String? = null,
    var resource: String? = null,
    var multiValueHeaders: Map<String, List<String>>? = null,
    var queryStringParameters: Map<String, String>? = null,
    var requestContext: RequestContext? = null,
    var pathParameters: Map<String, String>? = null,
    var isBase64Encoded: Boolean = false,
    var parameters: Map<String, String>? = null,
    var multiValueParameters: Map<String, List<String>>? = null,
    var operationId: String? = null,
)

data class RequestContext(
    var identity: Identity? = null,
    var httpMethod: String? = null,
    var requestId: String? = null,
    var requestTime: String? = null,
    var requestTimeEpoch: Long? = null,
)

data class Identity(
    var sourceIp: String? = null,
    var userAgent: String? = null,
)