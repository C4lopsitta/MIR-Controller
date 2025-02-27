package dev.robaldo.mir.models.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BotMissionsViewModel: ViewModel() {


    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
}