package dev.robaldo.mir.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Mission @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("guid") val guid: String,
    @JsonNames("name") val name: String,
    @JsonNames("url") val url: String
)
