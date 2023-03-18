package ru.otus.otuskotlin.marketplace.ws

import io.ktor.websocket.*

suspend fun WebSocketSession.wsPing() {
    send("Please enter your name")

    for (frame in incoming) {
        if (frame !is Frame.Text) continue

        val text = frame.readText()

        send(Frame.Text("Hi, $text!"))
    }
}
