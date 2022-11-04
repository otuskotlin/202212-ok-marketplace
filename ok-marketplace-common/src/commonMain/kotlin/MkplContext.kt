package ru.otus.otuskotlin.marketplace.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserPermissions
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

data class MkplContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),
    var settings: MkplCorSettings = MkplCorSettings.NONE,

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var principal: MkplPrincipalModel = MkplPrincipalModel.NONE,
    val permissionsChain: MutableSet<MkplUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var adRepo: IAdRepository = IAdRepository.NONE,
    var adRepoRead: MkplAd = MkplAd(),
    var adRepoPrepare: MkplAd = MkplAd(),
    var adRepoDone: MkplAd = MkplAd(),
    var adsRepoDone: MutableList<MkplAd> = mutableListOf(),

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: MkplAd = MkplAd(),
    var adFilterRequest: MkplAdFilter = MkplAdFilter(),

    var adValidating: MkplAd = MkplAd(),
    var adFilterValidating: MkplAdFilter = MkplAdFilter(),

    var adValidated: MkplAd = MkplAd(),
    var adFilterValidated: MkplAdFilter = MkplAdFilter(),

    var adResponse: MkplAd = MkplAd(),
    var adsResponse: MutableList<MkplAd> = mutableListOf(),
)
