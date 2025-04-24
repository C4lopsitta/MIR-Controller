package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WhereToVote
import androidx.compose.material.icons.rounded.WhereToVote
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.BotMap
import dev.robaldo.mir.models.view.BotMapsViewModel
import dev.robaldo.mir.models.view.BotViewModel

/**
 * The Maps route composable body.
 *
 * @param mapsViewModel The view model for the maps.
 * @param botViewModel The view model for the bot.
 *
 * @see BotMapsViewModel
 * @see BotViewModel
 *
 * @author Simone Robaldo
 */
@Composable
fun Maps(
    mapsViewModel: BotMapsViewModel,
    botViewModel: BotViewModel
) {
    val refreshState = rememberPullRefreshState(
        refreshing = mapsViewModel.isLoading.value,
        onRefresh = { mapsViewModel.update() }
    )

    var currentMap by remember { mutableStateOf<BotMap?>(null) }

    LaunchedEffect(botViewModel.isLoading.value) {
        if(botViewModel.isLoading.value || botViewModel.status.value == null) return@LaunchedEffect
        currentMap = MirApi.getMap(botViewModel.status.value!!.mapId)
    }
//
//    fun decodeBase64PngToImageBitmap(base64String: String): ImageBitmap? {
//        return try {
//            // Decode the Base64 string to a ByteArray
//            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
//            // Decode the PNG data to a Bitmap (Android-specific)
//            val bitmap = Image.makeFromEncoded(decodedBytes)
//            // Convert to an ImageBitmap for Compose
//            bitmap?.asImageBitmap()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }


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
        LazyColumn (
            modifier = Modifier.padding(12.dp).zIndex(-1f)
        ) {
            item {
                Text(
                    "Current Map",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            if(currentMap != null) {
                item {
                    Text(currentMap!!.name)
//                    Image(
//
//                    )
                }
            }
            item {
                Text(
                    "All Maps",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            items(mapsViewModel.maps.value) {
                ListItem(
                    headlineContent = { Text(it.name) },
                    supportingContent = { Text(it.guid) },
                    trailingContent = {
                        IconButton(
                            content = {
                                if(it.guid == botViewModel.status.value?.mapId) {
                                    Icon(Icons.Rounded.WhereToVote, contentDescription = null)
                                } else {
                                    Icon(Icons.Outlined.WhereToVote, contentDescription = null)
                                }
                            },
                            onClick = {
                                TODO("Gotta add call to set map")
                            }
                        )
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
