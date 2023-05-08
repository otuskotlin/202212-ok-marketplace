package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.helpers.errorEmptyId as mkplErrorEmptyId
import ru.otus.otuskotlin.marketplace.common.helpers.errorNotFound as mkplErrorNotFound
import ru.otus.otuskotlin.marketplace.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplError

data class DbAdResponse(
    override val data: MkplAd?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList()
): IDbResponse<MkplAd> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        fun success(result: MkplAd) = DbAdResponse(result, true)
        fun error(errors: List<MkplError>, data: MkplAd? = null) = DbAdResponse(data, false, errors)
        fun error(error: MkplError, data: MkplAd? = null) = DbAdResponse(data, false, listOf(error))

        val errorEmptyId = error(mkplErrorEmptyId)

        fun errorConcurrent(lock: MkplAdLock, ad: MkplAd?) = error(
            errorRepoConcurrency(lock, ad?.lock?.let { MkplAdLock(it.asString()) }),
            ad
        )

        val errorNotFound = error(mkplErrorNotFound)
    }
}
