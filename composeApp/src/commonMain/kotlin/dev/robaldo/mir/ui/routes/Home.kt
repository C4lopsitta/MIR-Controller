package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiTethering
import androidx.compose.material.icons.rounded.WifiTetheringOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.robaldo.mir.models.view.BotViewModel

/**
 * The Home route composable body.
 *
 * @param botViewModel The view model for the bot.
 * @param setFab The function to set the floating action button.
 *
 * @see BotViewModel
 *
 * @author Simone Robaldo
 */
@Composable
fun Home(
    botViewModel: BotViewModel,
    setFab: @Composable ((@Composable () -> Unit)?) -> Unit
) {
    setFab(null)

    Column {

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                if(botViewModel.status.value == null) Icons.Rounded.WifiTetheringOff else Icons.Rounded.WifiTethering,
                contentDescription = "Icon",
                tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(92.dp).height(92.dp)
            )
            Text(
                "MiR " + if(botViewModel.status.value == null) "Disconnected" else "Connected",
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding( horizontal = 32.dp, vertical = 12.dp ),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}