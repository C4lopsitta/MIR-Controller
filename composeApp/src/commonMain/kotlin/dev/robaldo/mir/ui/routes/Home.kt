package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Home(
    setTopBar: @Composable ((@Composable () -> Unit)?) -> Unit,
    setFab: @Composable ((@Composable () -> Unit)?) -> Unit
) {
    setFab(null)
    setTopBar(null)

    Column {
        Icon(Icons.Rounded.Home, contentDescription = "Home", modifier = Modifier.height(24.dp).width(24.dp))
        Text("Home UI")

    }
}