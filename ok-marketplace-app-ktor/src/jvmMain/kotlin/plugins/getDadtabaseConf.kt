package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.app.configs.CassandraConfig
import ru.otus.otuskotlin.marketplace.app.configs.PostgresConfig
import ru.otus.otuskotlin.marketplace.backend.repo.cassandra.RepoAdCassandra
import ru.otus.otuskotlin.marketplace.backend.repo.sql.RepoAdSQL
import ru.otus.otuskotlin.marketplace.backend.repo.sql.SqlProperties
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdRepoGremlin
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

actual fun Application.getDadtabaseConf(type: AdDbType): IAdRepository {
    val dbSettingPath = "db.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        "cassandra", "nosql", "cass" -> initCassandra()
        "arcade", "arcadedb", "graphdb", "gremlin", "g", "a" -> initGremliln()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

private fun Application.initPostgres(): IAdRepository {
    val config = PostgresConfig(environment.config)
    return RepoAdSQL(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,
        )
    )
}

private fun Application.initCassandra(): IAdRepository {
    val config = CassandraConfig(environment.config)
    return RepoAdCassandra(
        keyspaceName = config.keyspace,
        host = config.host,
        port = config.port,
        user = config.user,
        pass = config.pass,
    )
}

private fun Application.initGremliln(): IAdRepository {
    val prefix = "db.gremlin"
    val hostPath = "$prefix.host"
    val portPath = "$prefix.port"
    val userPath = "$prefix.user"
    val passPath = "$prefix.pass"
    val sslPath = "$prefix.enableSsl"
    val errorMessage = "must be set in application.conf/application.yml"
    val host = environment.config.propertyOrNull(hostPath)?.getString()
        ?: throw IllegalArgumentException("$hostPath $errorMessage")
    val port = environment.config.propertyOrNull(portPath)?.getString()?.toIntOrNull()
        ?: throw IllegalArgumentException("$portPath $errorMessage")
    val user = environment.config.propertyOrNull(userPath)?.getString()
        ?: throw IllegalArgumentException("$userPath $errorMessage")
    val pass = environment.config.propertyOrNull(passPath)?.getString()
        ?: throw IllegalArgumentException("$passPath $errorMessage")
    val ssl = environment.config.propertyOrNull(sslPath)?.getString()?.toBoolean()
        ?: false
    return AdRepoGremlin(
        hosts = host,
        port = port,
        user = user,
        pass = pass,
        enableSsl = ssl,
    )
}

private fun Application.initInMemory(): IAdRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return AdRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}
