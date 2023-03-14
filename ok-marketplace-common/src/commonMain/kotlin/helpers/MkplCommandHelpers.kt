package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

fun MkplContext.isUpdatableCommand() =
    this.command in listOf(MkplCommand.CREATE, MkplCommand.UPDATE, MkplCommand.DELETE)
