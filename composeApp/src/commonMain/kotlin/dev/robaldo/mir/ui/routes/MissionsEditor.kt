package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.missions.BotMissionAction
import dev.robaldo.mir.models.missions.BotMission
import dev.robaldo.mir.models.flows.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * The UI page for editing a Mission.
 *
 * @param navController The Application's [NavHostController]
 * @param guid The mission's GUID
 * @param uiEvents The Shared Flow to manage API Exceptions
 *
 * @see [dev.robaldo.mir.models.responses.get.Item]
 * @see [UiEvent]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionsEditor(
    navController: NavHostController,
    uiEvents: MutableSharedFlow<UiEvent>,
    guid: String,
    setFab: @Composable (@Composable () -> Unit) -> Unit
) {
    var missionData by remember { mutableStateOf<BotMission?>(null) }
    var actionsList by remember { mutableStateOf<List<BotMissionAction>>(emptyList()) }
    var showActionPickerSheet by remember { mutableStateOf(false) }
    var loadingProgress by remember { mutableStateOf(-0.1f) }

    setFab {
        FloatingActionButton(
            content = {
                Icon(
                    Icons.Rounded.AddBox,
                    contentDescription = "Add Action"
                )
            },
            onClick = {
                showActionPickerSheet = true
            }
        )
    }

    LaunchedEffect(Unit) {
        try {
            missionData = MirApi.getMission(guid)
            loadingProgress = .33f

            actionsList = MirApi.getMissionActions(missionData!!)
            loadingProgress = 1.1f

        } catch (ex: Exception) {
            uiEvents.emit(UiEvent.ApiError(ex))
        }
    }

    LazyColumn (
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        item {
            Text(
                "Edit Mission",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        if (loadingProgress <= 1f) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().fillParentMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (loadingProgress < 0f) {
                        CircularProgressIndicator()
                    } else {
                        CircularProgressIndicator(
                            progress = { loadingProgress }
                        )
                    }
                }
            }
        }

        if (missionData != null && loadingProgress > 1f) {
            item {
                OutlinedTextField(
                    value = missionData!!.name,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    onValueChange = { newName ->
                        missionData!!.name = newName
                    },
                    label = { Text("Mission Name") }
                )
            }
            item {
                Text(
                    "Actions",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                )
            }

            items(actionsList) {
                ListItem(
                    headlineContent = { Text(it.actionType) },
                    supportingContent = { Text(it.guid) }
                )
            }
        }
    }

    if( showActionPickerSheet ) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
            onDismissRequest = { showActionPickerSheet = false }
        ) {
            LazyColumn (
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                item {
                    ScrollableTabRow(
                        selectedTabIndex = 1,
                        edgePadding = 0.dp
                    ) {
                        Tab(false, {  }, text = { Text("Move") })
                        Tab(false, {  }, text = { Text("Battery") })
                        Tab(false, {  }, text = { Text("Logic") })
                        Tab(false, {  }, text = { Text("Error") })
                        Tab(false, {  }, text = { Text("Sound/Light") })
                        Tab(false, {  }, text = { Text("PLC") })
                        Tab(false, {  }, text = { Text("Email Address") })
                        Tab(false, {  }, text = { Text("I/O Modules") })
                        Tab(false, {  }, text = { Text("Shelf") })
                        Tab(false, {  }, text = { Text("Mission") })
                    }
                }
                item {
                    Text("Move")
                }
                item {
                    Text("Battery")
                }
                item {
                    Text("Logic")
                }
                item {
                    Text("Error")
                }
                item {
                    Text("Sound/Light")
                }
                item {
                    Text("PLC")
                }
                item {
                    Text("Email Address")
                }
                item {
                    Text("I/O Modules")
                }
                item {
                    Text("Shelf")
                }
                item {
                    Text("Mission")
                }

            }
        }
    }
}
