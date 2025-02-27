package dev.robaldo.mir.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotError(
    @SerialName("code") val code: Int?,
    @SerialName("description") val description: String?,
    @SerialName("module") val module: String?
)
