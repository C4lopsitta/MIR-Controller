package dev.robaldo.mir.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A detailed mission returned by the [dev.robaldo.mir.api.MirApi]
 *
 * @property guid The mission's GUID
 * @property name The name of the mission
 * @property isHidden If the mission is set as hidden or not on the Web Dashboard
 * @property createdByName TODO DEFINE
 * @property createdById TODO DEFINE
 * @property createdBy TODO DEFINE
 * @property definitionUrl The URL containing the parameters required for this mission
 * @property description The description of the mission
 * @property actionsUrl The URL that, when fetched, returns the list of actions of this mission
 * @property isTemplate If the mission is a template or not
 * @property isValid If the mission contains valid instructions
 * @property groupId The ID of the area this mission belongs to, null if can be used in any area
 * @property sessionId The ID of the session area, can be null
 * @property hasUserParameters Indicates if the mission has dynamic parameters or not
 */
@Serializable
data class BotMission(
    val guid: String,
    var name: String,
    @SerialName("hidden") val isHidden: Boolean,
    @SerialName("created_by_name") val createdByName: String,
    @SerialName("created_by") val createdBy: String,
    @SerialName("created_by_id") val createdById: String,
    @SerialName("definition") val definitionUrl: String,
    val description: String,
    @SerialName("actions") val actionsUrl: String,
    @SerialName("is_template") val isTemplate: Boolean,
    @SerialName("valid") val isValid: Boolean,
    @SerialName("group_id") val groupId: String?,
    @SerialName("session_id") val sessionId: String?,
    @SerialName("has_user_parameters") val hasUserParameters: Boolean
)
