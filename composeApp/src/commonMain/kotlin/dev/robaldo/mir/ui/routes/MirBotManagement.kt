package dev.robaldo.mir.ui.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.robaldo.mir.AppPreferences
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.enums.ConnectionTestStatus
import dev.robaldo.mir.models.view.BotViewModel
import dev.robaldo.mir.ui.components.DataPairRow
import dev.robaldo.mir.Log
import dev.robaldo.mir.exceptions.AuthenticationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


fun updateValue(lambda: suspend () -> Unit) {
    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        lambda()
    }
}


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

    var address by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isAuthenticationCollapsed by remember { mutableStateOf(botViewModel.status.value != null) }
    var isErrorsCollapsed by remember { mutableStateOf(true) }
    var connectionTestingStatus by remember { mutableStateOf(ConnectionTestStatus.TODO) }

    LaunchedEffect(Unit) {
        address = AppPreferences.getAddress()
        username = AppPreferences.getUsername()
        password = AppPreferences.getPassword()
    }

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
        }
        else {
            item {
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }

        item {
            Column {
                Row(
                    modifier = Modifier
                        .clickable { isAuthenticationCollapsed = !isAuthenticationCollapsed }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Authentication",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Icon(
                        if (isAuthenticationCollapsed) Icons.Rounded.ArrowDropDown else Icons.Rounded.ArrowDropUp,
                        contentDescription = ""
                    )
                }

                AnimatedVisibility(
                    visible = !isAuthenticationCollapsed
                ) {
                    Column {
                        OutlinedTextField(
                            value = address,
                            label = { Text("Address") },
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Rounded.Dns, contentDescription = "Address")
                            },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            onValueChange = {
                                address = it
                                updateValue {
                                    AppPreferences.setAddress(it)
                                }
                            },
                            placeholder = { Text("IP Address or Domain") }
                        )

                        OutlinedTextField(
                            value = username,
                            label = { Text("Username") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            isError = connectionTestingStatus == ConnectionTestStatus.FAILURE_AUTHENTICATION,
                            leadingIcon = {
                                Icon(Icons.Rounded.Person, contentDescription = "Username")
                            },
                            onValueChange = {
                                username = it
                                updateValue {
                                    AppPreferences.setUsername(it)
                                }
                            },
                            placeholder = { Text("Your Username") }
                        )

                        OutlinedTextField(
                            value = password,
                            label = { Text("Password") },
                            singleLine = true,
                            isError = connectionTestingStatus == ConnectionTestStatus.FAILURE_AUTHENTICATION,
                            leadingIcon = {
                                Icon(Icons.Rounded.Key, contentDescription = "Password")
                            },
                            trailingIcon = {
                                IconButton(
                                    content = {
                                        Icon(
                                            if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                            contentDescription = "Show Password"
                                        )
                                    },
                                    onClick = {
                                        isPasswordVisible = !isPasswordVisible
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            visualTransformation = if (isPasswordVisible) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                            onValueChange = {
                                password = it
                                updateValue {
                                    AppPreferences.setPassword(it)
                                }
                            },
                            placeholder = { Text("Your Password") }
                        )

                        OutlinedButton (
                            content = {
                                when(connectionTestingStatus) {
                                    ConnectionTestStatus.TODO -> Text("Test Connection")
                                    ConnectionTestStatus.IN_PROGRESS -> CircularProgressIndicator( modifier = Modifier.height(12.dp).width(12.dp) )
                                    ConnectionTestStatus.SUCCESS -> Text("Successfully Connected")
                                    ConnectionTestStatus.FAILURE -> Text("Failed to Connect")
                                    ConnectionTestStatus.FAILURE_AUTHENTICATION -> Text("Wrong Credentials")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    connectionTestingStatus = ConnectionTestStatus.IN_PROGRESS
                                    try {
                                        val result = MirApi.getMissions()
                                        connectionTestingStatus = ConnectionTestStatus.SUCCESS
                                    } catch (e: AuthenticationException) {
                                        connectionTestingStatus = ConnectionTestStatus.FAILURE_AUTHENTICATION
                                    } catch (e: Exception) {
                                        Log.e("MirBotManagement", "Failed to connect: ${e.message}", e)
                                        connectionTestingStatus = ConnectionTestStatus.FAILURE
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        if(botViewModel.status.value?.errors?.isNotEmpty() == true) {
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .clickable { isErrorsCollapsed = !isErrorsCollapsed }
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Errors",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                        Icon(
                            if (isErrorsCollapsed) Icons.Rounded.ArrowDropDown else Icons.Rounded.ArrowDropUp,
                            contentDescription = ""
                        )
                    }

                    AnimatedVisibility(
                        visible = !isErrorsCollapsed
                    ) {
                        Column {
                            botViewModel.status.value!!.errors.forEach { error ->
                                Text(error.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}
