package ru.otus.otuskotlin.marketplace.v1

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.addError
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.helpers.isUpdatableCommand
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

val sessions = mutableSetOf<WebSocketSession>()

suspend fun WebSocketSession.wsHandlerV1() {
    sessions.add(this)

    // Handle init request
    val ctx = MkplContext()
    val init = apiV1Mapper.writeValueAsString(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    // Handle flow
    incoming.receiveAsFlow().mapNotNull { it ->
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = MkplContext()

        // Handle without flow destruction
        try {
            val request = apiV1Mapper.readValue<IRequest>(jsonStr)
            context.fromTransport(request)
            context.adResponse = MkplAdStub.get()

            val result = apiV1Mapper.writeValueAsString(context.toTransportAd())

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

            val result = apiV1Mapper.writeValueAsString(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()
}
