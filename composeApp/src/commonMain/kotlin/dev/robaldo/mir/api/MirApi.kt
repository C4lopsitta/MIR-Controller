package dev.robaldo.mir.api

import dev.robaldo.mir.enums.BotBadgeStatus
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.models.Mission
import dev.robaldo.mir.models.requests.post.EnqueueMission
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object MirApi {
    private const val baseUrl = "/api/v2.0.0"
    private const val mirIp_TEMP = "192.168.12.20"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    private fun generateAuthToken(
        username: String,
        password: String
    ): String {
        val compound = "$username:$password"

        return ""
    }


    suspend fun getMissions(): List<Mission> {
        val response = client.get(Url("http://$mirIp_TEMP$baseUrl/missions")) {
            headers {
                append("Authorization", "Basic aXRpc2RlbHBvenpvOjlhZDVhYjA0NDVkZTE4ZDI4Nzg0NjMzNzNkNmRiZGIxZWUzZTFmZjg2YzBhYmY4OGJiMzU5YzNkYzVmMzBiNGQ=")
            }
        }
        println(response.bodyAsText())
        val missions = Json.decodeFromString<List<Mission>>(response.bodyAsText())
        return missions
    }


    suspend fun addMissionToQueue(mission: Mission): Boolean {
        val response = client.post(Url("http://$mirIp_TEMP$baseUrl/mission_queue")) {
            headers {
                append("Authorization", "Basic aXRpc2RlbHBvenpvOjlhZDVhYjA0NDVkZTE4ZDI4Nzg0NjMzNzNkNmRiZGIxZWUzZTFmZjg2YzBhYmY4OGJiMzU5YzNkYzVmMzBiNGQ=")
            }
            contentType(ContentType.Application.Json)
            setBody( EnqueueMission(mission.guid) )
        }

        println(response.bodyAsText())

        return response.status == HttpStatusCode.Created
    }

    suspend fun getBotStatus(): BotStatus? {
        val response = client.get(Url("http://$mirIp_TEMP$baseUrl/status")) {
            headers {
                append("Authorization", "Basic aXRpc2RlbHBvenpvOjlhZDVhYjA0NDVkZTE4ZDI4Nzg0NjMzNzNkNmRiZGIxZWUzZTFmZjg2YzBhYmY4OGJiMzU5YzNkYzVmMzBiNGQ=")
            }
        }

        if(response.status != HttpStatusCode.OK) return null
        return Json.decodeFromString<BotStatus>(response.bodyAsText())
    }
}
