package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.robaldo.mir.enums.BatteryStatus
import dev.robaldo.mir.models.BotStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    setTopBar: @Composable ((@Composable () -> Unit)?) -> Unit,
    setFab: @Composable ((@Composable () -> Unit)?) -> Unit,
    botStatus: BotStatus?
) {
    val botPercentage by remember {
        derivedStateOf{
            BatteryStatus.FromBatteryPercentage(botStatus?.battery_percentage ?: 0f)
        }
    }

    setFab(null)
    setTopBar {
        TopAppBar(
            title = { Text("MIR Robot") },
            actions = {
                Icon(botPercentage.toIcon(), contentDescription = null)
            }
        )
    }

    Column {
        Icon(Icons.Rounded.Home, contentDescription = "Home", modifier = Modifier.height(24.dp).width(24.dp))
        Text("Home UI")

    }
}