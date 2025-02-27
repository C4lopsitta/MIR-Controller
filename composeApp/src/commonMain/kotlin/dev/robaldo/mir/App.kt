package dev.robaldo.mir

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.api.caller
import dev.robaldo.mir.definitions.Routes
import dev.robaldo.mir.enums.BotBadgeStatus
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.models.view.BotMapsViewModel
import dev.robaldo.mir.models.view.BotMissionsViewModel
import dev.robaldo.mir.ui.components.AppNavigationBar
import dev.robaldo.mir.ui.components.TopBar
import dev.robaldo.mir.ui.routes.Home
import dev.robaldo.mir.ui.routes.Maps
import dev.robaldo.mir.ui.routes.MirBotManagement
import dev.robaldo.mir.ui.routes.Missions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
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
fun App(
    colorScheme: ColorScheme,
    missionsViewModel: BotMissionsViewModel = BotMissionsViewModel(),
    mapsViewModel: BotMapsViewModel = BotMapsViewModel()
) {
    // region app
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    var isLoading by remember { mutableStateOf(false) }
    // endregion app

    // region botStatus
    var botStatus by remember { mutableStateOf<BotStatus?>(null) }
    var botStatusPollingDelay by remember { mutableStateOf(500L) }
    var doAutoUpdateBotStatus by remember { mutableStateOf(true) }
    var reloadTrigger by remember { mutableStateOf(false) }
    // endregion botStatus

    val navController = rememberNavController()
    var fab by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    suspend fun updateBotStatus() {
        isLoading = true
        val status: BotStatus? = caller (snackbarHostState) {
            MirApi.getBotStatus()
        } as BotStatus?

        if(status == null) {
            botStatus = null
            return
        }

        botStatus = status
        isLoading = false
    }

    LaunchedEffect(key1 = reloadTrigger) {
        updateBotStatus()
        delay(botStatusPollingDelay)
        if(doAutoUpdateBotStatus) reloadTrigger = !reloadTrigger
    }

    MaterialTheme (
        colorScheme = colorScheme
    ) {
        Scaffold (
            bottomBar = {
                AppNavigationBar(
                    navHostController = navController,
                    botStatus = botStatus
                )
            },
            topBar = {
                TopBar(
                    title = "MIR",
                    botStatus = botStatus
                )
            },
            floatingActionButton = fab ?: {},
            snackbarHost = { SnackbarHost( snackbarHostState ) }
        ) { paddingValues ->
            if(
                mapsViewModel.isLoading.value ||
                missionsViewModel.isLoading.value
            ) LinearProgressIndicator( modifier = Modifier.fillMaxWidth() )

            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding( paddingValues )
            ) {
                composable(Routes.HOME) {
                    Home(
                        setFab = { fab = it },
                        botStatus = botStatus
                    )
                }

                composable(Routes.CONTROLLER) { Text("Controller") }
                composable(Routes.MISSIONS) {
                    Missions (
                        snackbarHostState = snackbarHostState,
                        setFab =  { fab = it }
                    )
                }
                composable(Routes.MAPS) {
                    Maps(
                        maps = mapsViewModel.maps.value
                    )
                }
                composable(Routes.ROBOT) {
                    MirBotManagement(
                        botStatus = botStatus,
                        setFab = { fab = it }
                    )
                }
            }
        }
    }
}