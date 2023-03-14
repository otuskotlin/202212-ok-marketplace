package ru.otus.otuskotlin.marketplace.serverlessapp.api.v1

import ru.otus.otuskotlin.marketplace.serverlessapp.api.IHandleStrategy

sealed interface IV1HandleStrategy : IHandleStrategy {
    override val version: String
        get() = V1

    companion object {
        const val V1 = "v1"
        private val strategies = listOf(
            CreateAdHandler,
            ReadAdHandler,
            UpdateAdHandler,
            DeleteAdHandler,
            SearchAdHandler,
            OffersAdHandler
        )
        val strategiesByDiscriminator = strategies.associateBy { it.path }
    }
}

