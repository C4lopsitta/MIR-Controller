package dev.robaldo.mir.models

import kotlinx.serialization.Serializable

@Serializable
data class BotStatus(
    val battery_percentage: Float,
    val battery_time_remaining: Int,
    val mission_queue_id: Int?,
    val mission_queue_url: String?,
    val mission_text: String?,
    val state_id: Int,
    val state_text: String,
    val mode_id: Int,
    val mode_text: String,
    val position: MirBotPosition,
    val velocity: MirBotVelocity,
    val errors: List<String>,
    val uptime: Int,
    val robot_name: String,
    val serial_number: String,
    val joystick_web_session_id: String
)

