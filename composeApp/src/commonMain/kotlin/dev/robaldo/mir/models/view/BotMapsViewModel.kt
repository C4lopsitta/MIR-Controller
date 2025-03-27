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

class BotMapsViewModel(
    private val uiEvents: MutableSharedFlow<UiEvent>
) : ViewModel() {
    private val _maps = mutableStateOf<List<Item>>(emptyList())
    val maps: State<List<Item>> = _maps

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        update()
    }

    fun update() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _maps.value = MirApi.getMaps()
            } catch (ex: Exception) {
                uiEvents.emit(UiEvent.ApiError(ex))
            } finally {
                _isLoading.value = false
            }
        }
    }
}


