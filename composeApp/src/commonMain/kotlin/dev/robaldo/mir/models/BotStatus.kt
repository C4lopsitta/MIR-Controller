package dev.robaldo.mir.models

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BotStatus constructor(
    @SerialName("battery_percentage") val batteryPercentage: Float,
    @SerialName("battery_time_remaining") val batteryTimeRemaining: Int,
    @SerialName("mission_queue_id") val missionQueueId: Int?,
    @SerialName("mission_queue_url") val missionQueueUrl: String?,
    @SerialName("mission_text") val missionText: String?,
    @SerialName("state_id") val stateId: Int,
    @SerialName("state_text") val stateText: String,
    @SerialName("mode_id") val modeId: Int,
    @SerialName("mode_text") val modeText: String,
    @SerialName("position") val position: MirBotPosition,
    @SerialName("velocity") val velocity: MirBotVelocity,
    @SerialName("errors") val errors: List<BotError>,
    @SerialName("uptime") val uptime: Int,
    @SerialName("robot_name") val robotName: String,
    @SerialName("serial_number") val serialNumber: String,
    @SerialName("joystick_web_session_id") val joystickSessionWebId: String
) : ViewModel() {

}

