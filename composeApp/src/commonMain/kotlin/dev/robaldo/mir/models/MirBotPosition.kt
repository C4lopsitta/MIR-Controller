package dev.robaldo.mir.models

import kotlinx.serialization.Serializable

/**
 * The MiR 100 Robot's current position
 *
 * @property x The X position
 * @property y The Y position
 * @property orientation The orientation of the robot in degrees from ranges -180 to 180
 *
 * @author Simone Robaldo
 */
@Serializable
data class MirBotPosition(
    val x: Float,
    val y: Float,
    val orientation: Float
)
