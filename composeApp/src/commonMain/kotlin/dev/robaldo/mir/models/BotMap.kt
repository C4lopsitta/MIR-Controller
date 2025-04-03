package dev.robaldo.mir.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64

/**
 * A Detailed Map data class returned by the [dev.robaldo.mir.api.MirApi].
 *
 * Contains more details compared to a [dev.robaldo.mir.models.responses.get.Item] instance. Includes also a Base64 encoded Map PNG.
 *
 * @property base64Map The Base64 encoded Map PNG.
 * @property positions TODO Define
 * @property name The name of the map.
 * @property paths TODO Define
 * @property oneWayMap TODO Define
 * @property createdByName The name of the user who created the map.
 * @property createdBy The ID of the user who created the map.
 * @property createdById The ID of the user who created the map.
 * @property sessionId TODO Define
 * @property originTheta TODO Define
 * @property originX TODO Define
 * @property originY TODO Define
 * @property pathGuides TODO Define
 * @property guid The ID of the map.
 * @property resolution TODO Define
 * @property metadata TODO Define Base64 data tho
 */
@Serializable
data class BotMap (
    @SerialName("map") val base64Map: String,
    @SerialName("positions") val positions: String,
    @SerialName("name") val name: String,
    @SerialName("paths") val paths: String,
    @SerialName("one_way_map") val oneWayMap: String,
    @SerialName("session_id") val sessionId: String,
    @SerialName("origin_theta") val originTheta: Float,
    @SerialName("origin_x") val originX: Float,
    @SerialName("origin_y") val originY: Float,
    @SerialName("path_guides") val pathGuides: String,
    @SerialName("resolution") val resolution: Float,
    @SerialName("metadata") val metadata: String,
    @SerialName("guid") val guid: String,
    @SerialName("created_by_name") val createdByName: String,
    @SerialName("created_by") val createdBy: String,
    @SerialName("created_by_id") val createdById: String,
)


