package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import dev.robaldo.mir.models.responses.get.Item
import dev.robaldo.mir.models.view.BotMapsViewModel

@Composable
fun Maps(
    mapsViewModel: BotMapsViewModel
) {
    PullRefreshLayout(
        state = rememberPullRefreshState(
            refreshing = mapsViewModel.isLoading.value,
            onRefresh = { mapsViewModel.update() }
        )
    ) {
        LazyColumn {
            items(mapsViewModel.maps.value) {
                Text(it.name ?: "")
            }
        }
    }
}
