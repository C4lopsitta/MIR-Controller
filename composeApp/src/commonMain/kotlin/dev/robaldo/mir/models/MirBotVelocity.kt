package dev.robaldo.mir.models

import kotlinx.serialization.Serializable

@Serializable
data class MirBotVelocity(
    val linear: Float,
    val angular: Float
)
