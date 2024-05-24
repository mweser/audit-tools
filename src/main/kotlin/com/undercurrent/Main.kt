package com.undercurrent

import com.undercurrent.querymodules.BlockchainQueryModule
import com.undercurrent.querymodules.DatabaseQueryModule
import com.undercurrent.querymodules.DefunctCryptoReceiveEvents
import kotlinx.coroutines.*
import java.io.File
import java.util.Properties

fun main() {
    // Load properties
    val properties = Properties().apply {
        load(File("config/local.properties").inputStream())
    }

    val pemFilePath = properties.getProperty("pemFilePath")
    val awsInstanceAddress = properties.getProperty("awsInstanceAddress")
    val databasePath = properties.getProperty("databasePath")

    // Example usage
    println("PEM File Path: $pemFilePath")
    println("AWS Instance Address: $awsInstanceAddress")
    println("Database Path: $databasePath")

    // Start scheduled coroutine job
    val auditService = AuditService(pemFilePath, awsInstanceAddress, databasePath)
    auditService.startCoroutineJob(auditService::performAudit, 0, 10000L)
}

class AuditService(
    private val pemFilePath: String,
    private val awsInstanceAddress: String,
    private val databasePath: String
) {
    private val blockchainQueryModule = BlockchainQueryModule()
    private val databaseQueryModule = DatabaseQueryModule(databasePath)

    fun startCoroutineJob(workerFunction: suspend () -> Unit, delayMs: Long = 0, periodMs: Long = 1000) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val job = coroutineScope.launch {
            delay(delayMs)
            while (isActive) {
                workerFunction()
                delay(periodMs)
            }
        }

        Runtime.getRuntime().addShutdownHook(Thread {
            runBlocking {
                job.cancelAndJoin()
                blockchainQueryModule.close()
            }
        })
    }

    suspend fun performAudit() {
        // Your audit logic here
        println("Performing audit...")
        val events = databaseQueryModule.getDefunctCryptoReceiveEvents()
        events.forEach {
            val address = it[DefunctCryptoReceiveEvents.receivingAddress]
            val blockchainResponse = blockchainQueryModule.getTransactionDetails(address)
            // Compare and log discrepancies
            println("Address: ${blockchainResponse.address}")
        }
    }
}
