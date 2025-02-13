package dev.robaldo.mir.models

import kotlinx.serialization.Serializable

@Serializable
data class MirBotPosition(
    val x: Float,
    val y: Float,
    val orientation: Float
)
