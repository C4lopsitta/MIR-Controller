package dev.robaldo.mir.models.flows

/**
 * A Sealed Class used by the ViewModels to communicate with the UI.
 *
 * @see dev.robaldo.mir.models.view
 * @author Simone Robaldo
 */
sealed class UiEvent {
    /**
     * A data class used to communicate an error to the UI.
     *
     * @param ex The exception that caused the error.
     * @author Simone Robaldo
     */
    data class ApiError(val ex: Exception): UiEvent()
}
