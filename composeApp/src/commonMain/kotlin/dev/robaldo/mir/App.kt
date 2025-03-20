package dev.robaldo.mir

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.robaldo.mir.definitions.Routes
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.models.flows.UiEvent
import dev.robaldo.mir.models.view.BotMapsViewModel
import dev.robaldo.mir.models.view.BotMissionsViewModel
import dev.robaldo.mir.models.view.BotViewModel
import dev.robaldo.mir.ui.components.AppNavigationBar
import dev.robaldo.mir.ui.components.TopBar
import dev.robaldo.mir.ui.routes.Home
import dev.robaldo.mir.ui.routes.Maps
import dev.robaldo.mir.ui.routes.MirBotManagement
import dev.robaldo.mir.ui.routes.Missions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
    uiEvents: MutableSharedFlow<UiEvent> = MutableSharedFlow(),
    missionsViewModel: BotMissionsViewModel = BotMissionsViewModel(),
    mapsViewModel: BotMapsViewModel = BotMapsViewModel(),
    botViewModel: BotViewModel = BotViewModel(uiEvents = uiEvents)
) {
    // region app
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    // endregion app

    // region botStatus
//    var botStatusPollingDelay by remember { mutableStateOf(500L) }
//    var doAutoUpdateBotStatus by remember { mutableStateOf(true) }
    // endregion botStatus

    val navController = rememberNavController()
    var fab by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    LaunchedEffect(uiEvents) {
        uiEvents.collect { event ->
            when(event) {
                is UiEvent.ApiError -> {
                    snackbarHostState.showSnackbar(
                        message = event.ex.message ?: "Undefined Error",
                        withDismissAction = true,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    MaterialTheme (
        colorScheme = colorScheme
    ) {
        Scaffold (
            bottomBar = {
                AppNavigationBar(
                    navHostController = navController,
                    botViewModel = botViewModel
                )
            },
            topBar = {
                TopBar(
                    title = "MIR",
                    botViewModel = botViewModel
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
                        botViewModel = botViewModel
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
                        botViewModel = botViewModel,
                        setFab = { fab = it }
                    )
                }
            }
        }
    }
}