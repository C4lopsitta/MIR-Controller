package dev.robaldo.mir

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.definitions.Routes
import dev.robaldo.mir.enums.BotBadgeStatus
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.ui.components.AppNavigationBar
import dev.robaldo.mir.ui.routes.Home
import dev.robaldo.mir.ui.routes.MirBotManagement
import dev.robaldo.mir.ui.routes.Missions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath
import org.jetbrains.compose.ui.tooling.preview.Preview

const val preferencesFile = "mir-prefs.preferences_pb"

@Composable expect fun storePreferences(): DataStore<Preferences>

fun instantiatePreferences(createPath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            createPath().toPath()
        }
    )


@Composable
@Preview
fun App(colorScheme: ColorScheme) {
    val navController = rememberNavController()
    var topBar by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var fab by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var botBadgeStatus by remember { mutableStateOf(BotBadgeStatus.DISCONNECTED) }
    var botStatus by remember { mutableStateOf<BotStatus?>(null) }

    CoroutineScope(Dispatchers.IO).launch {
        val status = MirApi.getBotStatus()

        if(status == null) {
            botBadgeStatus = BotBadgeStatus.DISCONNECTED
            botStatus = null
            return@launch
        }

        if(status.errors.isEmpty()) {
            botBadgeStatus = when (status.state_id) {
                3 -> BotBadgeStatus.READY
                4 -> BotBadgeStatus.PAUSED
                11 -> BotBadgeStatus.MANUAL_CONTROL
                else -> BotBadgeStatus.DISCONNECTED
            }
        } else {
            botBadgeStatus = BotBadgeStatus.ERROR
        }

        botStatus = status
    }

    MaterialTheme (
        colorScheme = colorScheme
    ) {
        Scaffold (
            bottomBar = {
                AppNavigationBar(
                    navHostController = navController,
                    botBadgeStatus = botBadgeStatus)
            },
            topBar = topBar ?: {},
            floatingActionButton = fab ?: {}
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding( paddingValues )
            ) {
                composable(Routes.HOME) { Home() }
                composable(Routes.CONTROLLER) { Text("Controller") }
                composable(Routes.MISSIONS) {
                    Missions (
                        setTopBar = { topBar = it },
                        setFab =  { fab = it }
                    )
                }
                composable(Routes.MAPS) { Text("Maps") }
                composable(Routes.ROBOT) { MirBotManagement() }
            }
        }
    }
}