package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.robaldo.mir.models.responses.get.Item

@Composable
fun Maps(
    maps: List<Item>
) {
    LazyColumn {
        items(maps) {
            Text(it.name ?: "")
        }
    }
}
