package com.undercurrent.querymodules
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class BlockchainResponse(
    val address: String,
    val chain_stats: ChainStats,
    val mempool_stats: MempoolStats
)

@Serializable
data class ChainStats(
    val funded_txo_count: Int,
    val funded_txo_sum: Long,
    val spent_txo_count: Int,
    val spent_txo_sum: Long,
    val tx_count: Int
)

@Serializable
data class MempoolStats(
    val funded_txo_count: Int,
    val funded_txo_sum: Long,
    val spent_txo_count: Int,
    val spent_txo_sum: Long,
    val tx_count: Int
)

class BlockchainQueryModule {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun getTransactionDetails(address: String): BlockchainResponse {
        val url = "https://blockstream.info/api/address/$address"
        return client.get(url).body()
    }

    fun close() {
        client.close()
    }
}

fun main() {
    runBlocking {
        val blockchainQueryModule = BlockchainQueryModule()
        val address = "17BLZiWuDMD4cUpBWqLxJhGDAaCrn4APoK"
        val response = blockchainQueryModule.getTransactionDetails(address)
        println("Address: ${response.address}")
        println("Funded TXO Count: ${response.chain_stats.funded_txo_count}")
        println("Funded TXO Sum: ${response.chain_stats.funded_txo_sum}")
        blockchainQueryModule.close()
    }
}
