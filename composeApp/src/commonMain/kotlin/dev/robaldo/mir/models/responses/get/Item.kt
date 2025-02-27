package dev.robaldo.mir.models.responses.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item (
    @SerialName("guid") val guid: String,
    @SerialName("url") val url: String,
    @SerialName("name") val name: String
)
