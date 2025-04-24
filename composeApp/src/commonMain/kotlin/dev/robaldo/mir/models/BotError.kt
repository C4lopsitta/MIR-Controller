package dev.robaldo.mir.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A serializable class containing error information returned by the MiR 100 Robot API.
 *
 * @property code The error code.
 * @property description The error description.
 * @property module The module where the error occurred.
 *
 * @see dev.robaldo.mir.api.MirApi
 * @see dev.robaldo.mir.models.BotStatus
 *
 * @author Simone Robaldo
 */
@Serializable
data class BotError(
    @SerialName("code") val code: Int?,
    @SerialName("description") val description: String?,
    @SerialName("module") val module: String?
) {
    override fun toString(): String {
        return "BotError(code=$code, description=$description, module=$module)"
    }
}

