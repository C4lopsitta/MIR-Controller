package dev.robaldo.mir.ui.routes

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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Queue
import androidx.compose.material.icons.rounded.SwipeDownAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.view.BotMissionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * The Missions route composable body.
 *
 * @param missionsViewModel The view model for the missions.
 * @param navController The Application's [NavHostController]
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
    val refreshState = rememberPullRefreshState(
        refreshing = missionsViewModel.isLoading.value,
        onRefresh = { missionsViewModel.update() }
    )

    PullRefreshLayout(
        state = refreshState,
        modifier = Modifier.fillMaxSize(),
        indicator = {
            PullRefreshIndicator(
                state = refreshState,
                modifier = Modifier.zIndex(1f)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(12.dp).zIndex(-1f)
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
            if(missionsViewModel.missions.value.isEmpty()) {
                item {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Rounded.SwipeDownAlt,
                            contentDescription = "Icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.width(92.dp).height(92.dp)
                        )
                        Text(
                            "Pull to Refresh",
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding( horizontal = 32.dp, vertical = 12.dp ),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Try to refresh the missions list.",
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding( horizontal = 32.dp, vertical = 8.dp ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(64.dp))
                }
            }
        }
    }
}

