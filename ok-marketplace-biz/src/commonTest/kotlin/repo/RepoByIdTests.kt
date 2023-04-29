package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.AdRepositoryMock
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.MkplCorSettings
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbAdResponse
import kotlin.test.assertEquals

private val initAd = MkplAd(
    id = MkplAdId("123"),
    title = "abc",
    description = "abc",
    adType = MkplDealSide.DEMAND,
    visibility = MkplVisibility.VISIBLE_PUBLIC,
)
private val repo = AdRepositoryMock(
        invokeReadAd = {
            if (it.id == initAd.id) {
                DbAdResponse(
                    isSuccess = true,
                    data = initAd,
                )
            } else DbAdResponse(
                isSuccess = false,
                data = null,
                errors = listOf(MkplError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    MkplCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { MkplAdProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: MkplCommand) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        adRequest = MkplAd(
            id = MkplAdId("12345"),
            title = "xyz",
            description = "xyz",
            adType = MkplDealSide.DEMAND,
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
        ),
    )
    processor.exec(ctx)
    assertEquals(MkplState.FAILING, ctx.state)
    assertEquals(MkplAd(), ctx.adResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
