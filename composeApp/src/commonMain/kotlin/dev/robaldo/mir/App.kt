package dev.robaldo.mir

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material3.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import dev.robaldo.mir.models.flows.UiEvent
import dev.robaldo.mir.models.responses.get.Item
import dev.robaldo.mir.models.view.BotMapsViewModel
import dev.robaldo.mir.models.view.BotMissionsViewModel
import dev.robaldo.mir.models.view.BotViewModel
import dev.robaldo.mir.ui.components.AppNavigationBar
import dev.robaldo.mir.ui.components.TopBar
import dev.robaldo.mir.ui.routes.Home
import dev.robaldo.mir.ui.routes.Maps
import dev.robaldo.mir.ui.routes.MirBotManagement
import dev.robaldo.mir.ui.routes.Missions
import dev.robaldo.mir.ui.routes.MissionsEditor
import kotlinx.coroutines.flow.MutableSharedFlow
import okio.Path.Companion.toPath
import org.jetbrains.compose.ui.tooling.preview.Preview

const val preferencesFile = "mir-prefs.preferences_pb"

/**
 * Expected function to instantiate preferences data store on each platform.
 *
 * @return A preferences data store.
 *
 * @see storePreferences
 * @see preferencesFile
 *
 * @author Simone Robaldo
 */
@Composable expect fun storePreferences(): DataStore<Preferences>

/**
 * Instantiates a preferences data store.
 *
 * @param createPath A lambda that returns the path to the preferences file.
 *
 * @see storePreferences
 * @see preferencesFile
 *
 * @return A preferences data store.
 */
fun instantiatePreferences(createPath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            createPath().toPath()
        }
    )

/**
 * CommonMain Entrypoint
 *
 * @param colorScheme the app's color scheme, used by Android to set the material you color scheme.
 * @param uiEvents the flow of UI events. Set by default.
 * @param missionsViewModel the view model for the missions. Set by default.
 * @param mapsViewModel the view model for the maps. Set by default.
 * @param botViewModel the view model for the bot. Set by default.
 *
 * @see BotViewModel
 * @see BotMissionsViewModel
 * @see BotMapsViewModel
 * @see AppNavigationBar
 * @see TopBar
 * @see Home
 * @see Maps
 * @see MirBotManagement
 * @see Missions
 *
 * @author Simone Robaldo
 */
@Composable
@Preview
fun App(
    colorScheme: ColorScheme,
    uiEvents: MutableSharedFlow<UiEvent> = MutableSharedFlow(),
    missionsViewModel: BotMissionsViewModel = BotMissionsViewModel(uiEvents = uiEvents),
    mapsViewModel: BotMapsViewModel = BotMapsViewModel(uiEvents = uiEvents),
    botViewModel: BotViewModel = BotViewModel(uiEvents = uiEvents)
) {
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }

//    var botStatusPollingDelay by remember { mutableStateOf(500L) }
//    var doAutoUpdateBotStatus by remember { mutableStateOf(true) }

    val navController = rememberNavController()
    var fab by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var showBackButton by remember { mutableStateOf(false) }

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
                    botViewModel = botViewModel,
                    showBackButton = showBackButton,
                    navController = navController
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
                    showBackButton = false
                    Home( botViewModel = botViewModel ) { fab = it }
                }

                composable(Routes.CONTROLLER) {
                    showBackButton = false
                    Text("Controller")
                }

                composable(Routes.MISSIONS) {
                    showBackButton = false
                    fab = {
                        FloatingActionButton(
                            content = {
                                Icon(
                                    Icons.Rounded.Add,
                                    contentDescription = "Add Mission"
                                )
                            },
                            onClick = {
                                val newMissionItem = Item("", "", "")

                                navController.navigate("missions/${newMissionItem.guid}")
                            }
                        )
                    }
                    Missions ( navController = navController, missionsViewModel = missionsViewModel )
                }

                composable(Routes.MISSION_EDITOR) {
                    showBackButton = true
                    val guid = it.arguments?.getString("guid") ?: return@composable
                    var openSheet: () -> Unit = {}
                    MissionsEditor( navController = navController, guid = guid, uiEvents = uiEvents) { fab = it }
                }

                composable(Routes.MAPS) {
                    showBackButton = false
                    Maps(
                        mapsViewModel = mapsViewModel,
                        botViewModel = botViewModel
                    )
                }
                composable(Routes.ROBOT) {
                    showBackButton = false
                    MirBotManagement( botViewModel = botViewModel ) { fab = it }
                }
            }
        }
    }
}