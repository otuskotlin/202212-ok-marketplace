package ru.otus.otuskotlin.markeplace.springapp.api.v1.ws

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.helpers.isUpdatableCommand
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportAd
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportInit

@Component
class WsAdHandlerV1 : TextWebSocketHandler() {
    private val sessions = mutableMapOf<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions[session.id] = session

        val context = MkplContext()

        val msg = apiV1Mapper.writeValueAsString(context.toTransportInit())
        session.sendMessage(TextMessage(msg))
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val ctx = MkplContext(timeStart = Clock.System.now())

        runBlocking {
            try {
                val request = apiV1Mapper.readValue(message.payload, IRequest::class.java)
                ctx.fromTransport(request)

                val result = apiV1Mapper.writeValueAsString(ctx.toTransportAd())
                if (ctx.isUpdatableCommand()) {
                    sessions.values.forEach {
                        it.sendMessage(TextMessage(result))
                    }
                } else {
                    session.sendMessage(TextMessage(result))
                }
            } catch (e: Exception) {
                ctx.errors.add(e.asMkplError())

                val response = apiV1Mapper.writeValueAsString(ctx.toTransportInit())
                session.sendMessage(TextMessage(response))
            }
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
    }
}
