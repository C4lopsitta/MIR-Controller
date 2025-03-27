package dev.robaldo.mir.models

import androidx.lifecycle.ViewModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Class containing the bot's status details.
 *
 * @property batteryPercentage The battery percentage floating point value
 * @property batteryTimeRemaining The battery time remaining in seconds
 * @property missionQueueId TODO Define
 * @property missionQueueUrl The URL of the mission queue
 * @property missionText TODO Define
 * @property stateId The current status of the robot
 * @property stateText The current status of the robot as a string
 * @property modeId TODO Define
 * @property modeText TODO Define
 * @property mapId The currently used map's GUID
 * @property position The current position of the robot
 * @property velocity The current velocity of the robot
 * @property errors A list of [BotError]s
 * @property uptime The uptime of the robot in seconds
 * @property robotName The name of the robot
 * @property serialNumber The serial number of the robot
 * @property joystickSessionWebId The web session ID of the joystick
 *
 * @see BotError
 * @see MirBotPosition
 * @see MirBotVelocity
 * @see dev.robaldo.mir.models.view.BotViewModel
 *
 * @author Simone Robaldo
 */
@Serializable
class BotStatus (
    @SerialName("battery_percentage") val batteryPercentage: Float,
    @SerialName("battery_time_remaining") val batteryTimeRemaining: Int,
    @SerialName("mission_queue_id") val missionQueueId: Int?,
    @SerialName("mission_queue_url") val missionQueueUrl: String?,
    @SerialName("mission_text") val missionText: String?,
    @SerialName("state_id") val stateId: Int,
    @SerialName("state_text") val stateText: String,
    @SerialName("mode_id") val modeId: Int,
    @SerialName("map_id") val mapId: String,
    @SerialName("mode_text") val modeText: String,
    @SerialName("position") val position: MirBotPosition,
    @SerialName("velocity") val velocity: MirBotVelocity,
    @SerialName("errors") val errors: List<BotError>,
    @SerialName("uptime") val uptime: Int,
    @SerialName("robot_name") val robotName: String,
    @SerialName("serial_number") val serialNumber: String,
    @SerialName("joystick_web_session_id") val joystickSessionWebId: String
)

