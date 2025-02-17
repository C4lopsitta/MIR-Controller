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
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Filter
import androidx.compose.material.icons.rounded.Filter1
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Queue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.em
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.api.caller
import dev.robaldo.mir.models.Mission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Missions(
    snackbarHostState: SnackbarHostState,
    setTopBar: @Composable (@Composable () -> Unit) -> Unit,
    setFab: @Composable (@Composable () -> Unit) -> Unit
) {
    var missions by remember { mutableStateOf<List<Mission>>(emptyList()) }

    LaunchedEffect(Unit) {
        missions = (caller(snackbarHostState) {
            MirApi.getMissions()
        } ?: emptyList<Mission>()) as List<Mission>
    }

    setTopBar {
        TopAppBar(
            title = { Text("Missions") },
            actions = {
                IconButton(
                    content = { Icon(Icons.Rounded.FilterAlt, contentDescription = "Filter missions") },
                    onClick = {

                    }
                )
            }
        )
    }

    setFab {
        FloatingActionButton(
            content = { Icon(Icons.Rounded.Add, contentDescription = "Add Mission") },
            onClick = {

            }
        )
    }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {
            items(missions) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(it.name, fontSize = 3.em, fontWeight = if(it.name.contains("suono", ignoreCase = true)) FontWeight.Bold else FontWeight.Normal)
                        Text(it.guid, fontSize = 2.em)
                    }
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
                HorizontalDivider()
            }
            item {
                Box( modifier = Modifier.fillMaxWidth().height(64.dp) )
            }
        }
    }
}