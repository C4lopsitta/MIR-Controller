package dev.robaldo.mir.enums

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class BotBadgeStatus {
    DISCONNECTED,
    READY,
    PAUSED,
    EXECUTING,
    MANUAL_CONTROL,
    ERROR,
    EMERGENCY_STOP;

    @Composable
    fun toColor(): Color {
        return when(this) {
            DISCONNECTED -> if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray
            READY -> Color.Green
            PAUSED -> Color(red = 0xff, green = 0xa6, blue = 0x00)
            EXECUTING -> Color(red = 0x98, green = 0x18, blue = 0xdd)
            MANUAL_CONTROL -> Color.Blue
            ERROR -> Color.Red
            EMERGENCY_STOP -> Color.Red
        }
    }

    companion object {
        fun fromStatus(status: Int): BotBadgeStatus {
            return when (status) {
                3 -> READY
                4 -> PAUSED
                5 -> EXECUTING
                10 -> EMERGENCY_STOP
                11 -> MANUAL_CONTROL
                12 -> ERROR
                else -> DISCONNECTED
            }
        }
    }
}
