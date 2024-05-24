package com.undercurrent.querymodules

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DefunctCryptoReceiveEvents : Table("DEFUNCT_crypto_receive_events") {
    val receivingAddress = varchar("receivingAddress", 10000)
    val amount = varchar("amount", 10000)
    val currencyType = varchar("currencyType", 10000)
    val transactionData = varchar("transactionData", 10000)
    val createdDate = varchar("createdDate", 10000)
    val updatedDate = varchar("updatedDate", 10000)
    val expiryEpoch = varchar("expiryEpoch", 10000)
}

class DatabaseQueryModule(databasePath: String) {
    init {
        Database.connect("jdbc:sqlite:$databasePath", driver = "org.sqlite.JDBC")
    }

    fun getDefunctCryptoReceiveEvents(): List<ResultRow> {
        return transaction {
            DefunctCryptoReceiveEvents.selectAll().toList()
        }
    }
}

fun main() {
    val databaseQueryModule = DatabaseQueryModule("path/to/your/database.db")
    val events = databaseQueryModule.getDefunctCryptoReceiveEvents()
    events.forEach {
        println("Address: ${it[DefunctCryptoReceiveEvents.receivingAddress]}, Amount: ${it[DefunctCryptoReceiveEvents.amount]}")
    }
}
