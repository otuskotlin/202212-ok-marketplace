package ru.otus.otuskotlin.marketplace.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdResponse
import ru.otus.otuskotlin.marketplace.common.repo.DbAdsResponse
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class RepoAdSQL(
    properties: SqlProperties,
    initObjects: Collection<MkplAd> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IAdRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(AdTable)
            SchemaUtils.create(AdTable)
            initObjects.forEach { createAd(it) }
        }
    }

    private fun createAd(ad: MkplAd): MkplAd {
        val res = AdTable.insert {
            to(it, ad, randomUuid)
        }

        return AdTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbAdResponse): DbAdResponse =
        transactionWrapper(block) { DbAdResponse.error(it.asMkplError()) }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse = transactionWrapper {
        DbAdResponse.success(createAd(rq.ad))
    }

    private fun read(id: MkplAdId): DbAdResponse {
        val res = AdTable.select {
            AdTable.id eq id.asString()
        }.singleOrNull() ?: return DbAdResponse.errorNotFound
        return DbAdResponse.success(AdTable.from(res))
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: MkplAdId,
        lock: MkplAdLock,
        block: (MkplAd) -> DbAdResponse
    ): DbAdResponse =
        transactionWrapper {
            if (id == MkplAdId.NONE) return@transactionWrapper DbAdResponse.errorEmptyId

            val current = AdTable.select { AdTable.id eq id.asString() }
                .firstOrNull()
                ?.let { AdTable.from(it) }

            when {
                current == null -> DbAdResponse.errorNotFound
                current.lock != lock -> DbAdResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }


    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse = update(rq.ad.id, rq.ad.lock) {
        AdTable.update({ AdTable.id eq rq.ad.id.asString() }) {
            to(it, rq.ad.copy(lock = MkplAdLock(randomUuid())), randomUuid)
        }
        read(rq.ad.id)
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse = update(rq.id, rq.lock) {
        AdTable.deleteWhere { id eq rq.id.asString() }
        DbAdResponse.success(it)
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse =
        transactionWrapper({
            val res = AdTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != MkplUserId.NONE) {
                        add(AdTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.dealSide != MkplDealSide.NONE) {
                        add(AdTable.dealSide eq rq.dealSide)
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (AdTable.title like "%${rq.titleFilter}%")
                                    or (AdTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbAdsResponse(data = res.map { AdTable.from(it) }, isSuccess = true)
        }, {
            DbAdsResponse.error(it.asMkplError())
        })
}
