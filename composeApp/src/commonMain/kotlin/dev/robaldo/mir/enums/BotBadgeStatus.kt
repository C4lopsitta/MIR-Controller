package dev.robaldo.mir.enums

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class BotBadgeStatus {
    DISCONNECTED,
    READY,
    PAUSED,
    MANUAL_CONTROL,
    ERROR;

    @Composable
    fun toColor(): Color {
        return when(this) {
            DISCONNECTED -> if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray
            READY -> Color.Green
            PAUSED -> Color(red = 0xff, green = 0xa6, blue = 0x00)
            MANUAL_CONTROL -> Color.Blue
            ERROR -> Color.Red
        }
    }
}