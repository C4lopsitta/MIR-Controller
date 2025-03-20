package dev.robaldo.mir.models.flows

sealed class UiEvent {
    data class ApiError(val ex: Exception): UiEvent()
}
