package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdDeleteTest {
    abstract val repo: IAdRepository
    protected open val deleteSucc = initObjects[0]

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteAd(DbAdIdRequest(deleteSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("delete") {
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val notFoundId = MkplAdId("ad-repo-delete-notFound")
    }
}
