package ru.otus.otuskotlin.markeplace.springapp.controllers.v1.ws

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfigV1(val handlerV1: WsAdHandlerV1) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(handlerV1, "/ws/v1").setAllowedOrigins("*")
    }
}
