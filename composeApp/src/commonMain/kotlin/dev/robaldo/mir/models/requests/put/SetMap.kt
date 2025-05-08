package dev.robaldo.mir.models.requests.put

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Dataclass to serialize the map id for the put
 * @property mapId the id of the map
 * @author Marco Garro
 */
@Serializable
data class SetMap(
    @SerialName("map_id") var mapId : String
)
