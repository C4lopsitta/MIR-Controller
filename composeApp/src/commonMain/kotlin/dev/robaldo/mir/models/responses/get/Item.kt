package dev.robaldo.mir.models.responses.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Basic class for a generic, non detailed, item returned by the API.
 *
 * @property guid Unique ID of the item.
 * @property url The URL, not including the host, of the resource
 * @property name The name of the item.
 *
 * @sample dev.robaldo.mir.docSamples.ItemSample
 * @see [dev.robaldo.mir.api.MirApi]
 */
@Serializable
data class Item (
    @SerialName("guid") val guid: String,
    @SerialName("url") val url: String,
    @SerialName("name") val name: String
)
