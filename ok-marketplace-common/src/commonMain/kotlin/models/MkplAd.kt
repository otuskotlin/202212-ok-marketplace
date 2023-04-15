package ru.otus.otuskotlin.marketplace.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.NONE
import ru.otus.otuskotlin.marketplace.common.statemachine.SMAdStates

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
    val permissionsClient: MutableSet<MkplAdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): MkplAd = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

}
