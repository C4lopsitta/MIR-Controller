package dev.robaldo.mir.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotMap(
    @SerialName("map") val map: String,
    @SerialName("positions") val positions: String,
    @SerialName("name") val name: String,
    @SerialName("paths") val paths: String,
    @SerialName("one_way_map") val oneWayMap: String,
    @SerialName("created_by_name") val createdByName: String,
    @SerialName("created_by") val createdBy: String,
    @SerialName("created_by_id") val createdById: String,
    @SerialName("session_id") val sessionId: String,
    @SerialName("origin_theta") val originTheta: Float,
    @SerialName("origin_x") val originX: Float,
    @SerialName("origin_y") val originY: Float,
    @SerialName("path_guides") val pathGuides: String,
    @SerialName("guid") val guid: String,
    @SerialName("resolution") val resolution: Float,
    @SerialName("metadata") val metadata: String,
)