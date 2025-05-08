package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.robaldo.mir.api.DeliveryApi
import dev.robaldo.mir.models.delivery.InventoryItem

@Composable
fun Delivery() {
    var inventory by remember { mutableStateOf<List<InventoryItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        inventory = DeliveryApi.getInventory()
    }

    LazyColumn {
        items(inventory) {
            ListItem(
                headlineContent = { Text(it.name) },
                supportingContent = { Text(it.uid) }
            )
            HorizontalDivider()
        }
    }
}
