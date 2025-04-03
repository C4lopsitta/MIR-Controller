package dev.robaldo.mir.enums

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zakgof.korender.math.ColorRGB
import com.zakgof.korender.math.ColorRGBA

/**
 * An enum value corresponding to the status of the MiR 100 robot.
 *
 * @author Simone Robaldo
 */
enum class BotBadgeStatus {
    DISCONNECTED,
    READY,
    PAUSED,
    EXECUTING,
    MANUAL_CONTROL,
    ERROR,
    EMERGENCY_STOP;

    /**
     * Returns the corresponding color value of the status to be used with a Badge.
     *
     * @see androidx.compose.material3.Badge
     * @see dev.robaldo.mir.ui.components.AppNavigationBar
     * @see dev.robaldo.mir.ui.routes.MirBotManagement
     *
     * @author Simone Robaldo
     */
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

    fun toModelColor(): ColorRGB {
        return when(this) {
            DISCONNECTED -> ColorRGB(1f, 0f, 0f)
            READY -> ColorRGB.Green
            PAUSED -> ColorRGB(1f, 0.75f, 0f)
            EXECUTING -> ColorRGB(0.8f, 0.1f, 0.7f)
            MANUAL_CONTROL -> ColorRGB.Blue
            ERROR -> ColorRGB.Red
            EMERGENCY_STOP -> ColorRGB.Red
        }
    }

    companion object {
        /**
         * Returns the corresponding enumeration value of the given integer.
         *
         * @param status The integer value of the status.
         * @author Simone Robaldo
         */
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
