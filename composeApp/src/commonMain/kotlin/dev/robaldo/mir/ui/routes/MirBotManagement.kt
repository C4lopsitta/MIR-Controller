package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.robaldo.mir.enums.BatteryStatus
import dev.robaldo.mir.enums.BotBadgeStatus
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.ui.components.DataPairRow
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MirBotManagement(
    botStatus: BotStatus?,
    setTopBar: @Composable ((@Composable () -> Unit)?) -> Unit,
    setFab: @Composable ((@Composable () -> Unit)?) -> Unit
) {
    val botPercentage by remember {
        derivedStateOf{
            BatteryStatus.FromBatteryPercentage(botStatus?.battery_percentage ?: 0f)
        }
    }

    setFab(null)
    setTopBar {
        TopAppBar(
            title = {
                Column {
                    Text("Status", style = MaterialTheme.typography.titleLarge)
                    Text(
                        botStatus?.state_text ?: "MIR Bot is Disconnected",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            actions = {
                Icon(botPercentage.toIcon(), contentDescription = null, modifier = Modifier.padding( horizontal = 12.dp ))
            }
        )
    }

    LazyColumn (
        modifier = Modifier.padding( horizontal = 12.dp )
    ) {
        if(botStatus != null) {
            item { Text(botStatus.robot_name, style = MaterialTheme.typography.titleLarge) }
            item {
                DataPairRow(
                    labelLeft = "Battery",
                    labelRight = "Uptime",
                    dataLeft = {
                        Row {
                            Icon(botPercentage.toIcon(), contentDescription = null)
                            Text(
                                "${botStatus.battery_percentage.roundToInt()}%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            )
                        }
                    },
                    dataRight = {
                        Text("${botStatus.uptime}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
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
                            Icon(Icons.Rounded.Circle, contentDescription = null, tint = BotBadgeStatus.FromStatus(botStatus.state_id).toColor())
                            Text(
                                botStatus.state_text,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            )
                        }
                    },
                    dataRight = {
                        Text(botStatus.mode_text, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
            }

            item {
                DataPairRow(
                    labelLeft = "Position",
                    labelRight = "Orientation",
                    dataLeft = {
                        Text("X: ${String.format("%.3f", botStatus.position.x)}; Y: ${String.format("%.3f", botStatus.position.y)}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    },
                    dataRight = {
                        Text("${"%.3f".format(botStatus.position.orientation + 180)} degrees", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
                DataPairRow(
                    labelLeft = "Linear Velocity",
                    labelRight = "Angular Velocity",
                    dataLeft = {
                        Text("${botStatus.velocity.linear}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    },
                    dataRight = {
                        Text("${botStatus.velocity.angular}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }
                )
                HorizontalDivider()
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
