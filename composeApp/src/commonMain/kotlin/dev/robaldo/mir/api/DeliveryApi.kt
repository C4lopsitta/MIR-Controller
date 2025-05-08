package dev.robaldo.mir.api

import dev.robaldo.mir.models.delivery.InventoryItem
import dev.robaldo.mir.models.responses.get.DeliveryInventoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.http.Url
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

object DeliveryApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getInventory(): List<InventoryItem> {
        val response = client.get(Url("http://192.168.99.26:8080/api/v1/inventory")) {
            headers {
                append("Authorization", "")
            }
        }

        val json = Json { ignoreUnknownKeys = false }
        return json.decodeFromString<DeliveryInventoryResponse>(response.bodyAsText()).`object`
    }

    suspend fun getClassrooms() {

    }

    suspend fun postOrder() {

    }

    suspend fun getOrders() {
        
    }
}