package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.robaldo.mir.models.BotStatus

class BotStatusViewModel: ViewModel() {
    private val _botStatus = mutableStateOf<BotStatus?>(null)
    val botStatus: State<BotStatus?> = _botStatus
}
