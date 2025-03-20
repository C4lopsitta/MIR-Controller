package dev.robaldo.mir.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.robaldo.mir.enums.BatteryStatus
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.models.view.BotViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    botViewModel: BotViewModel,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val botPercentage by derivedStateOf {
        BatteryStatus.FromBatteryPercentage(botViewModel.status.value?.batteryPercentage ?: 0f)
    }

    TopAppBar(
        title = {
            Column {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Text(
                    botViewModel.status.value?.stateText ?: "MIR Bot is Disconnected",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        actions = {
            actions()
            Icon(botPercentage.toIcon(), contentDescription = null, modifier = Modifier.padding( horizontal = 12.dp ))
        }
    )
}