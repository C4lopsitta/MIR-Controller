package dev.robaldo.mir.models.requests.post

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class EnqueueMission @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("mission_id") var mission_id : String
)
