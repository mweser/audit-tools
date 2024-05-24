package com.undercurrent.querymodules

import com.undercurrent.utils.SystemUtils
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

abstract class DaoTable(
    name: String,
) : IntIdTable(name = name) {
    protected val CORE_VARCHAR_SIZE = 50000

    val createdDate = datetime("created_date").clientDefault { SystemUtils.currentUTC() }
    val updatedDate = datetime("updated_date").clientDefault { SystemUtils.currentUTC() }
    val expiryEpoch = long("expiry_epoch").nullable()
}