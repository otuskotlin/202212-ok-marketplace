package ru.otus.otuskotlin.marketplace.ws

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.*
import kotlin.collections.LinkedHashMap

private val sessions = Collections.synchronizedMap<WebSocketSession, UserSessionData>(LinkedHashMap())

suspend fun WebSocketSession.wsChat() {
    send("Please enter your name (name: <name>) and message (msg: <message>) or close")

    incoming.receiveAsFlow().mapNotNull { frame ->
        if (frame !is Frame.Text) return@mapNotNull

        val text: String = frame.readText()

        try {
            when {
                text.startsWith("name:") -> {
                    sessions.remove(this)

                    val name = text.drop(5).trim()
                    sessions[this] = UserSessionData(this, name)
                    send("Hi, $name!")
                }

                text.startsWith("msg:") -> {
                    val current = sessions.getValue(this)

                    sessions.filterKeys { it != this }.values.forEach {
                        val message = text.drop(4).trim()
                        it.session.send("[${current.name}]: $message")
                    }
                }

                text == "close" -> {
                    val current = sessions.getValue(this)

                    close(CloseReason(CloseReason.Codes.NORMAL, "Client left chat room"))
                    sessions.remove(this)
                    sessions.values.forEach {
                        it.session.send("[${current.name}]: left.")
                    }
                }
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.collect()
}

data class UserSessionData(
    val session: WebSocketSession,
    val name: String,
)
