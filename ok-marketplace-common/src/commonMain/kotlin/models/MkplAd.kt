package ru.otus.otuskotlin.marketplace.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.NONE
import ru.otus.otuskotlin.marketplace.common.statemachine.SMAdStates
import ru.otus.otuskotlin.marketplace.common.permissions.MkplAdPermissionClient
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalRelations

data class MkplAd(
    var id: MkplAdId = MkplAdId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
    var adType: MkplDealSide = MkplDealSide.NONE,
    var adState: SMAdStates = SMAdStates.NONE,
    var views: Int = 0,
    var timePublished: Instant = Instant.NONE,
    var timeUpdated: Instant = Instant.NONE,
    var visibility: MkplVisibility = MkplVisibility.NONE,
    var productId: MkplProductId = MkplProductId.NONE,
    var lock: MkplAdLock = MkplAdLock.NONE,
    var principalRelations: Set<MkplPrincipalRelations> = emptySet(),
    val permissionsClient: MutableSet<MkplAdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): MkplAd = copy(
        principalRelations = principalRelations.toSet(),
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        val NONE = MkplAd()
    }

}
