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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.robaldo.mir.models.view.BotViewModel

/**
 * The app's top bar
 *
 * @param title The title of the app
 * @param botViewModel An instance of the [BotViewModel] class to be used to display the Battery Percentage Icon
 * @param actions The actions to be displayed on the right side of the top bar
 *
 * @author Simone Robaldo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    botViewModel: BotViewModel,
    actions: @Composable RowScope.() -> Unit = {}
) {
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
            Icon(botViewModel.batteryStatus.value.toIcon(), contentDescription = null, modifier = Modifier.padding( horizontal = 12.dp ))
        }
    )
}