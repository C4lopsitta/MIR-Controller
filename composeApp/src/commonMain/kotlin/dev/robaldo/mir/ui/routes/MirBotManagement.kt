package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.robaldo.mir.models.view.BotViewModel
import dev.robaldo.mir.ui.components.DataPairRow
import kotlin.math.roundToInt

/**
 * The composable that displays the bot management options.
 *
 * @param botViewModel The view model for the bot.
 * @param setFab The function to set the floating action button.
 *
 * @see BotViewModel
 * @see DataPairRow
 *
 * @author Simone Robaldo
 */
@Composable
fun MirBotManagement(
    botViewModel: BotViewModel,
    setFab: @Composable ((@Composable () -> Unit)?) -> Unit
) {
    setFab(null)

    LazyColumn (
        modifier = Modifier.padding( horizontal = 12.dp )
    ) {
        if(botViewModel.status.value != null) {
            item { Text(botViewModel.status.value!!.robotName, style = MaterialTheme.typography.titleLarge) }
            item {
                DataPairRow(
                    labelLeft = "Battery",
                    labelRight = "Uptime",
                    dataLeft = {
                        Row {
                            Icon(botViewModel.batteryStatus.value.toIcon(), contentDescription = null)
                            Text(
                                "${botViewModel.status.value!!.batteryPercentage.roundToInt()}%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            )
                        }
                    },
                    dataRight = {
                        Text("${botViewModel.status.value!!.uptime}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
            }

            item {
                DataPairRow(
                    labelLeft = "Status",
                    labelRight = "Mode",
                    dataLeft = {
                        Row (
                            horizontalArrangement = Arrangement.spacedBy( 8.dp )
                        ) {
                            Icon(Icons.Rounded.Circle, contentDescription = null, tint = botViewModel.badge.value.toColor())
                            Text(
                                botViewModel.status.value!!.stateText,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            )
                        }
                    },
                    dataRight = {
                        Text(botViewModel.status.value!!.modeText, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
            }

            item {
                DataPairRow(
                    labelLeft = "Position",
                    labelRight = "Orientation",
                    dataLeft = {
                        Text("X: ${String.format("%.3f", botViewModel.status.value!!.position.x)}; Y: ${String.format("%.3f", botViewModel.status.value!!.position.y)}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    },
                    dataRight = {
                        Text("${"%.1f".format(botViewModel.status.value!!.position.orientation + 180)} degrees", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
                DataPairRow(
                    labelLeft = "Linear Velocity",
                    labelRight = "Angular Velocity",
                    dataLeft = {
                        Text("${botViewModel.status.value!!.velocity.linear}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    },
                    dataRight = {
                        Text("${botViewModel.status.value!!.velocity.angular}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
            }

            if(botViewModel.status.value!!.errors.isNotEmpty()) {
                item {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Error, contentDescription = "Error", modifier = Modifier.padding( end = 4.dp ))
                        Text(
                            "Error${if (botViewModel.status.value!!.errors.size > 1) "s" else ""}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
                items(botViewModel.status.value!!.errors) {
                    Text((it.description ?: "Unknown Error") + " (${it.code})")
                }
                item {
                    HorizontalDivider()
                }
            }
        } else {
            item {
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    }
}
