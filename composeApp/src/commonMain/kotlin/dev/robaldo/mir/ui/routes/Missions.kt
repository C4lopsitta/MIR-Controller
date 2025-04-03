package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Queue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.responses.get.Item
import dev.robaldo.mir.models.view.BotMissionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * The Missions route composable body.
 *
 * @param missionsViewModel The view model for the missions.
 * @param setFab The function to set the floating action button.
 *
 * @see BotMissionsViewModel
 *
 * @author Simone Robaldo
 */
@Composable
fun Missions(
    navController: NavHostController,
    missionsViewModel: BotMissionsViewModel,
) {
    PullRefreshLayout(
        state = rememberPullRefreshState(
            refreshing = missionsViewModel.isLoading.value,
            onRefresh = { missionsViewModel.update() }
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {
            items(missionsViewModel.missions.value) {
                ListItem(
                    headlineContent = {
                        Text(it.name)
                    },
                    supportingContent = {
                        Text(it.guid)
                    },
                    trailingContent = {
                        Row {
                            IconButton(
                                content = {
                                    Icon(
                                        Icons.Rounded.Edit,
                                        contentDescription = "Edit Mission"
                                    )
                                },
                                onClick = {
                                    navController.navigate("missions/${it.guid}")
                                }
                            )
                            IconButton(
                                content = {
                                    Icon(
                                        Icons.Rounded.Queue,
                                        contentDescription = "Add mission to queue"
                                    )
                                },
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        MirApi.addMissionToQueue(mission = it)
                                    }
                                }
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
            item {
                Box( modifier = Modifier.fillMaxWidth().height(64.dp) )
            }
        }
    }
}