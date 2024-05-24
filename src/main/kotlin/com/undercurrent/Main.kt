package com.undercurrent

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

    // Example usage
    println("PEM File Path: $pemFilePath")
    println("AWS Instance Address: $awsInstanceAddress")

    // Start scheduled coroutine job
    val auditService = AuditService(pemFilePath, awsInstanceAddress)
    auditService.startCoroutineJob(::performAudit, 0, 10000L)
}

class AuditService(private val pemFilePath: String, private val awsInstanceAddress: String) {
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
            }
        })
    }
}

suspend fun performAudit() {
    // Your audit logic here
    println("Performing audit...")
}
