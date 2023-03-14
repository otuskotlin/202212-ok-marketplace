package ru.otus.otuskotlin.marketplace.serverlessapp.api.v2

import ru.otus.otuskotlin.marketplace.serverlessapp.api.IHandleStrategy

sealed interface IV2HandleStrategy : IHandleStrategy {
    override val version: String
        get() = V2

    companion object {
        const val V2 = "v2"
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

