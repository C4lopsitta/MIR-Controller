package dev.robaldo.mir.models.status

import kotlinx.serialization.Serializable

/**
 * The MiR 100 Robot's current velocity
 *
 * @property linear The linear velocity
 * @property angular The angular velocity
 *
 * @author Simone Robaldo
 */
@Serializable
data class MirBotVelocity(
    val linear: Float,
    val angular: Float
)
