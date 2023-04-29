package ru.otus.otuskotlin.marketplace.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.model.AdEntity
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class AdRepoInMemory(
    initObjects: List<MkplAd> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IAdRepository {

    private val cache = Cache.Builder<String, AdEntity>()
        .expireAfterWrite(ttl)
        .build()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: MkplAd) {
        val entity = AdEntity(ad)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val key = randomUuid()
        val ad = rq.ad.copy(id = MkplAdId(key))
        val entity = AdEntity(ad)
        cache.put(key, entity)
        return DbAdResponse(
            data = ad,
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbAdResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val newAd = rq.ad.copy()
        val entity = AdEntity(newAd)
        return when (cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.put(key, entity)
                DbAdResponse(
                    data = newAd,
                    isSuccess = true,
                )
            }
        }
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        return when (val oldAd = cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.invalidate(key)
                DbAdResponse(
                    data = oldAd.toInternal(),
                    isSuccess = true,
                )
            }
        }
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != MkplUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.dealSide.takeIf { it != MkplDealSide.NONE }?.let {
                    it.name == entry.value.adType
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbAdsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbAdResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                MkplError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbAdResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                MkplError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
