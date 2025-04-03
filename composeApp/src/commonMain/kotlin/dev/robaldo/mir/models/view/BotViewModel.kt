package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robaldo.mir.Controller
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

    val batteryStatus = derivedStateOf {
        if(_status.value != null) BatteryStatus.fromBatteryPercentage(_status.value!!.batteryPercentage) else BatteryStatus.EMPTY_0
    }

    val badge: State<BotBadgeStatus> = derivedStateOf {
        if(_status.value != null) BotBadgeStatus.fromStatus(_status.value!!.stateId) else BotBadgeStatus.DISCONNECTED
    }

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            update()
        }

        polling()
    }

    private suspend fun update() {
        _isLoading.value = true
        try {
            _status.value = MirApi.getBotStatus()
        } catch (ex: Exception) {
            uiEvents.emit(UiEvent.ApiError(ex))
        } finally {
            _isLoading.value = false
        }
    }

    private fun polling() {
        viewModelScope.launch {
            update()
            println(Controller.getGameControllerIds())
            println(Controller.getLeftJoystickValues())
            delay(500L)
            polling()
        }
    }
}
