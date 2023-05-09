package ru.otus.otuskotlin.marketplace.app.configs

import io.ktor.server.config.*

data class CassandraConfig(
    val host: String = "localhost",
    val port: Int = 9042,
    val user: String = "cassandra",
    val pass: String = "cassandra",
    val keyspace: String = "test_keyspace"
) {
    constructor(config: ApplicationConfig) : this(
        host = config.property("$PATH.host").getString(),
        port = config.property("$PATH.port").getString().toInt(),
        user = config.property("$PATH.user").getString(),
        pass = config.property("$PATH.pass").getString(),
        keyspace = config.property("$PATH.keyspace").getString()
    )

    companion object {
        const val PATH = "marketplace.repository.cassandra"
    }
}
