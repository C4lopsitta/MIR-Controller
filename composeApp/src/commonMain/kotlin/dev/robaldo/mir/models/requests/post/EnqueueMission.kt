package dev.robaldo.mir.models.requests.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TODO Documentation and Implementation.
 */
@Serializable
data class EnqueueMission (
    @SerialName("mission_id") var missionId : String
)
