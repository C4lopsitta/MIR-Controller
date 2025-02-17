package dev.robaldo.mir.api

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import io.ktor.client.plugins.HttpRequestTimeoutException

suspend fun caller(
    snackbarHostState: SnackbarHostState,
    log: Boolean = true,
    endpoint: suspend () -> Any?
): Any? {
    try {
        return endpoint()
    } catch (ex: HttpRequestTimeoutException) {
        if(log) println(ex.toString())

        snackbarHostState.showSnackbar(
            message = ex.message ?: "Undefined Error",
            withDismissAction = true,
            duration = SnackbarDuration.Long
        )
//    } catch () {

    } catch (ex: Exception) {
        if(log) println(ex.toString())

        snackbarHostState.showSnackbar(
            message = ex.message ?: "Undefined Error",
            withDismissAction = true,
            duration = SnackbarDuration.Long
        )
    }
    return null
}
