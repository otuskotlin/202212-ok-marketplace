package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.decodeFromString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.encodeResponse
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.addError
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.helpers.isUpdatableCommand
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportInit
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

val sessions = mutableSetOf<WebSocketSession>()

suspend fun WebSocketSession.wsHandlerV2() {
    sessions.add(this)

    // Handle init request
    val ctx = MkplContext()
    val init = apiV2Mapper.encodeResponse(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    // Handle flow
    incoming.receiveAsFlow().mapNotNull { it ->
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = MkplContext()

        // Handle without flow destruction
        try {
            val request = apiV2Mapper.decodeFromString<IRequest>(jsonStr)
            context.fromTransport(request)
            context.adResponse = MkplAdStub.get()

            val result = apiV2Mapper.encodeResponse(context.toTransportAd())

            // If change request, response is sent to everyone
            if (context.isUpdatableCommand()) {
                sessions.forEach {
                    it.send(Frame.Text(result))
                }
            } else {
                outgoing.send(Frame.Text(result))
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (t: Throwable) {
            context.addError(t.asMkplError())

            val result = apiV2Mapper.encodeResponse(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()
}
