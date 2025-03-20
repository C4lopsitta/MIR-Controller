package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.responses.get.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BotMapsViewModel : ViewModel() {
    private val _maps = mutableStateOf<List<Item>>(emptyList())
    val maps: State<List<Item>> = _maps

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        initMaps()
    }

    fun initMaps() {
        viewModelScope.launch {
//            _isLoading.value = true
//            val mapsResult = withContext(Dispatchers.IO) {
//                caller {
//                    MirApi.getMaps()
//                } as List<Item>?
//            }
//            _maps.value = mapsResult ?: emptyList()
//            _isLoading.value = false
        }
    }
}


