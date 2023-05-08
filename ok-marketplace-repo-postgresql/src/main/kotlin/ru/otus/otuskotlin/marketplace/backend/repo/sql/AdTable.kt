package ru.otus.otuskotlin.marketplace.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility

object AdTable : Table("ad") {
    val id = varchar("id", 128)
    val title = varchar("title", 128)
    val description = varchar("description", 512)
    val owner = varchar("owner", 128)
    val visibility = enumeration("visibility", MkplVisibility::class)
    val dealSide = enumeration("deal_side", MkplDealSide::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res : InsertStatement<Number>) =MkplAd(
        id = MkplAdId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = MkplUserId(res[owner].toString()),
        visibility = res[visibility],
        adType = res[dealSide],
        lock = MkplAdLock(res[lock])
    )

    // копипаста, можно избавиться, сделав свой интерфейс и обертки над InsertStatement и ResultRow
    // но ради двух методов нет смысла
    fun from(res : ResultRow) = MkplAd(
        id = MkplAdId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = MkplUserId(res[owner].toString()),
        visibility = res[visibility],
        adType = res[dealSide],
        lock = MkplAdLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, ad: MkplAd, randomUuid: () -> String) {
        it[id] = ad.id.takeIf { it != MkplAdId.NONE }?.asString() ?: randomUuid()
        it[title] = ad.title
        it[description] = ad.description
        it[owner] = ad.ownerId.asString()
        it[visibility] = ad.visibility
        it[dealSide] = ad.adType
        it[lock] = ad.lock.takeIf { it != MkplAdLock.NONE }?.asString() ?: randomUuid()
    }

}