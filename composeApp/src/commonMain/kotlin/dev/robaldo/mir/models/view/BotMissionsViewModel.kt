package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.flows.UiEvent
import dev.robaldo.mir.models.responses.get.Item
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Maps data.
 *
 * @param uiEvents The MutableSharedFlow of UiEvents.
 * @property missions The stateful list of Mission [Item]s returned by the API.
 * @property isLoading The stateful boolean value to be used in the UI to show a loading indicator.
 * @see dev.robaldo.mir.models.flows.UiEvent
 * @author Simone Robaldo
 */
class BotMissionsViewModel(
    private val uiEvents: MutableSharedFlow<UiEvent>,
): ViewModel() {
    private val _missions = mutableStateOf<List<Item>>(emptyList())
    val missions: State<List<Item>> = _missions

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        update()
    }

    /**
     * Fetches the data asynchronously from the [MirApi] and stores it in the ViewModel.
     *
     * @author Simone Robaldo
     */
    fun update() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _missions.value = MirApi.getMissions()
            } catch (ex: Exception) {
                uiEvents.emit(UiEvent.ApiError(ex))
            } finally {
                _isLoading.value = false
            }
        }
    }
}