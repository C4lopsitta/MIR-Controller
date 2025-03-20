package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.enums.BatteryStatus
import dev.robaldo.mir.enums.BotBadgeStatus
import dev.robaldo.mir.models.BotStatus
import dev.robaldo.mir.models.flows.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BotViewModel(
    private val uiEvents: MutableSharedFlow<UiEvent>,
): ViewModel() {
    private val _status = mutableStateOf<BotStatus?>(null)
    val status: State<BotStatus?> = _status

    private val _batteryStatus = mutableStateOf<BatteryStatus?>(null)
    val batteryStatus: State<BatteryStatus?> = _batteryStatus

    private val _badge = mutableStateOf<BotBadgeStatus?>(null)
    val badge: State<BotBadgeStatus?> = _badge

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            update()
        }

        polling()
    }

    private suspend fun update() {
        try {
            _isLoading.value = true
            _status.value = MirApi.getBotStatus()
            _isLoading.value = false
        } catch (ex: Exception) {
            uiEvents.emit(UiEvent.ApiError(ex))
        }
    }

    private fun polling() {
        viewModelScope.launch {
            update()
            delay(500L)
            polling()
        }
    }
}
