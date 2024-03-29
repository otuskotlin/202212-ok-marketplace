package ru.otus.otuskotlin.marketplace.app

import com.auth0.jwt.JWT
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.app.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.otuskotlin.marketplace.app.base.resolveAlgorithm
import ru.otus.otuskotlin.marketplace.app.plugins.initAppSettings
import ru.otus.otuskotlin.marketplace.app.plugins.swagger
import ru.otus.otuskotlin.marketplace.app.v1.v1Ad
import ru.otus.otuskotlin.marketplace.app.v1.v1Offer
import ru.otus.otuskotlin.marketplace.app.v1.wsHandlerV1
import ru.otus.otuskotlin.marketplace.logging.jvm.MpLogWrapperLogback

fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

@Suppress("unused") // Referenced in application.conf_
fun Application.moduleJvm(
    appSettings: MkplAppSettings = initAppSettings(),
) {
    module(appSettings)

    install(CallLogging) {
        level = Level.INFO
        val lgr = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? MpLogWrapperLogback
        lgr?.logger?.also { logger = it }
    }
    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }
    install(DefaultHeaders)

    install(Authentication) {
        jwt("auth-jwt") {
            val authConfig = appSettings.auth
            realm = authConfig.realm

            verifier {
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        this@moduleJvm.log.error("Groups claim must not be empty in JWT token")
                        null
                    }

                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }

    routing {
        route("v1") {
            authenticate("auth-jwt") {
                v1Ad(appSettings)
                v1Offer(appSettings)
            }
            webSocket("/ws") {
                wsHandlerV1(appSettings)
            }
        }
        swagger(appSettings)
        static("static") {
            resources("static")
        }
    }
}
