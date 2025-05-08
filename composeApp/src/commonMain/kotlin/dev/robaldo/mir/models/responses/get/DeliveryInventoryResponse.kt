package dev.robaldo.mir.models.responses.get

import dev.robaldo.mir.models.delivery.InventoryItem
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryInventoryResponse(
    val `object`: List<InventoryItem>,
    val message: String,
    val httpStatus: Int,
    val success: Boolean
)
