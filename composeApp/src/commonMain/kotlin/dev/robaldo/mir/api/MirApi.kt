package dev.robaldo.mir.api

import dev.robaldo.mir.models.missions.BotMissionAction
import dev.robaldo.mir.models.BotMap
import dev.robaldo.mir.models.missions.BotMission
import dev.robaldo.mir.models.status.BotStatus
import dev.robaldo.mir.models.requests.post.EnqueueMission
import dev.robaldo.mir.models.responses.get.Item
import dev.robaldo.mir.Log
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

/**
 * Object used to interface with the MiR 100 Robot.
 *
 * @property TOKEN_TEMP Is a temporary, to be replaced, token value.
 * @property MIR_ROBOT_IP_TEMP Is a temporary, to be replaced, IP value, corresponding to the Robot's IP
 * @property BASE_URL The base URL of the API. Set to `/api/v2.0.0`
 * @sample dev.robaldo.mir.docSamples.MirApiSample
 * @author Robaldo Simone
 */
object MirApi {
    // This token is a temporary value that will be replaced with a calculated one with task "Token and Authorization"
    private const val TOKEN_TEMP =
        "Basic aXRpc2RlbHBvenpvOjlhZDVhYjA0NDVkZTE4ZDI4Nzg0NjMzNzNkNmRiZGIxZWUzZTFmZjg2YzBhYmY4OGJiMzU5YzNkYzVmMzBiNGQ="
    private const val MIR_ROBOT_IP_TEMP = "192.168.12.20"

    private const val BASE_URL = "/api/v2.0.0"

    /**
     * Definition of the HTTP Client with JSON Serialization and ContentNegotiation Support.
     */
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

    /**
     * Fetches basic Mission information and returns them in a list of [Item] objects.
     *
     * @throws Exception If the request fails.
     * @return A list of [Item] objects.
     * @author Simone Robaldo
     */
    suspend fun getMissions(): List<Item> {
        val response = client.get(Url("http://$MIR_ROBOT_IP_TEMP$BASE_URL/missions")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
        }

        Log.d("MirApi.getMissions()", response.bodyAsText())

        val missions = Json.decodeFromString<List<Item>>(response.bodyAsText())
        return missions
    }

    suspend fun getMission(guid: String): BotMission {
        val response = client.get(Url("http://$MIR_ROBOT_IP_TEMP$BASE_URL/missions/$guid")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
        }

        Log.d("MirApi.getMission()", response.bodyAsText())
        if(response.status != HttpStatusCode.OK) throw Exception(response.bodyAsText())
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<BotMission>(response.bodyAsText())
    }

    suspend fun getMissionActions(missionData: BotMission): List<BotMissionAction> {
        val response = client.get(Url("http://$MIR_ROBOT_IP_TEMP/api${missionData.actionsUrl}")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
        }

        Log.d("MirApi.getMissionActions()", response.bodyAsText())
        if(response.status != HttpStatusCode.OK) throw Exception(response.bodyAsText())

        val json = Json { ignoreUnknownKeys = true }
        println(response.bodyAsText())
        return json.decodeFromString<List<BotMissionAction>>(response.bodyAsText())
    }

    /**
     * Adds the given Mission [Item] to the Queue of the MiR 100 robot.
     *
     * @param mission The mission to add to the queue.
     * @throws Exception If the request fails.
     * @return A [Boolean] value indicating if the request was successful.
     * @author Simone Robaldo
     */
    suspend fun addMissionToQueue(mission: Item): Boolean {
        val response = client.post(Url("http://$MIR_ROBOT_IP_TEMP$BASE_URL/mission_queue")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
            contentType(ContentType.Application.Json)
            setBody( EnqueueMission(mission.guid) )
        }

        return response.status == HttpStatusCode.Created
    }

    /**
     * Fetches the current status of the MiR 100 robot.
     *
     * @throws Exception If the request fails.
     * @return A [BotStatus] object, null when the status of the response wasn't successful. The [BotStatus] object is intended to be used inside the [dev.robaldo.mir.models.view.BotViewModel]
     * @author Simone Robaldo
     */
    suspend fun getBotStatus(): BotStatus? {
        val response = client.get(Url("http://$MIR_ROBOT_IP_TEMP$BASE_URL/status")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
        }

        Log.d("MirApi.getBotStatus()", response.bodyAsText())

        if(response.status != HttpStatusCode.OK) return null
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<BotStatus>(response.bodyAsText())
    }

    /**
     * Fetches the maps stored on the MiR 100 Robot and returns them.
     *
     * @throws Exception If the request fails.
     * @return A List of [Item]s containing basic information about the maps.
     * @author Simone Robaldo
     */
    suspend fun getMaps(): List<Item> {
        val response = client.get(Url("http://$MIR_ROBOT_IP_TEMP$BASE_URL/maps")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
        }

        Log.d("MirApi.getMaps()", response.bodyAsText())

        if(response.status != HttpStatusCode.OK) return emptyList()

        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<List<Item>>(response.bodyAsText())
    }

    /**
     * Fetches an individual map from the MiR 100 Robot.
     *
     * @param map The Map GUID to fetch. Usually stored inside an [Item] returned by the [MirApi.getMaps] method.
     * @throws Exception If the request fails.
     * @return A [BotMap] object containing more details about the Map.
     * @author Simone Robaldo
     */
    suspend fun getMap(map: String): BotMap {
        val response = client.get(Url("http://$MIR_ROBOT_IP_TEMP$BASE_URL/maps/$map")) {
            headers {
                append("Authorization", TOKEN_TEMP)
            }
        }

        Log.d("MirApi.getMap()", response.bodyAsText())

        if(response.status != HttpStatusCode.OK) throw Exception("Could not load map")

        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<BotMap>(response.bodyAsText())
    }
}
