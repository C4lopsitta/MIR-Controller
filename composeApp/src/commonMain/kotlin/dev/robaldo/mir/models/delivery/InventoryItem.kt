package dev.robaldo.mir.models.delivery

import kotlinx.serialization.Serializable

@Serializable
data class InventoryItem(
    val uid: String,
    val name: String,
    val availableQuantity: Int,
    val img: String
)
