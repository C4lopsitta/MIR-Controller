package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.enums.BatteryStatus
import dev.robaldo.mir.enums.BotBadgeStatus
import dev.robaldo.mir.models.flows.UiEvent
import dev.robaldo.mir.models.status.BotStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Maps data.
 *
 * @param uiEvents The MutableSharedFlow of UiEvents.
 * @property status The stateful value of the robot as a [BotStatus] class instance.
 * @property batteryStatus The stateful value of the battery as a [BatteryStatus] enumeration value.
 * @property badge The stateful value of the badge as a [BotBadgeStatus] enumeration value.
 * @property isLoading The stateful boolean value to be used in the UI to show a loading indicator.
 * @see dev.robaldo.mir.models.flows.UiEvent
 * @see BotStatus
 * @see BatteryStatus
 * @see BotBadgeStatus
 * @author Simone Robaldo
 */
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

    /**
     * Fetches the data asynchronously from the [MirApi] and stores it in the ViewModel.
     *
     * @author Simone Robaldo
     */
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

    /**
     * Indefinitely polls the API every 500ms and updates the ViewModel.
     *
     * @author Simone Robaldo
     */
    private fun polling() {
        viewModelScope.launch {
            update()
            delay(500L)
            polling()
        }
    }
}
