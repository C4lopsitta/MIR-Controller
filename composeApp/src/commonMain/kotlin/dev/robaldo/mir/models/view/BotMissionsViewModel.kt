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