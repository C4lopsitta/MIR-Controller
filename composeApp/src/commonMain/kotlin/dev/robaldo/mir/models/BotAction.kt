package dev.robaldo.mir.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotAction(
    val guid: String,
    @SerialName("created_by_id") val createdById: String? = null,
    @SerialName("action_type") val actionType: String,
//    val parameters: String,
    @SerialName("scope_reference") val scopeReference: String? = null,
    @SerialName("mission_id") val missionId: String,
    val priority: Int
)
