package dev.robaldo.mir.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Battery0Bar
import androidx.compose.material.icons.rounded.Battery1Bar
import androidx.compose.material.icons.rounded.Battery2Bar
import androidx.compose.material.icons.rounded.Battery3Bar
import androidx.compose.material.icons.rounded.Battery4Bar
import androidx.compose.material.icons.rounded.Battery5Bar
import androidx.compose.material.icons.rounded.Battery6Bar
import androidx.compose.material.icons.rounded.BatteryFull
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Enumeration of the battery status, to be used to get the relative icon of the battery.
 *
 * @author Robaldo Simone
 */
enum class BatteryStatus {
    FULL_7,
    ALMOST_FULL_6,
    ALMOST_MID_FULL_5,
    MID_4,
    ALMOST_LOW_3,
    LOW_2,
    ALMOST_EMPTY_1,
    EMPTY_0;

    /**
     * Converts the current Enum Value into an [ImageVector] object.
     *
     * @return An [ImageVector] battery icon.
     * @author Simone Robaldo
     */
    fun toIcon(): ImageVector {
        return when(this) {
            FULL_7 -> Icons.Rounded.BatteryFull
            ALMOST_FULL_6 -> Icons.Rounded.Battery6Bar
            ALMOST_MID_FULL_5 -> Icons.Rounded.Battery5Bar
            MID_4 -> Icons.Rounded.Battery4Bar
            ALMOST_LOW_3 -> Icons.Rounded.Battery3Bar
            LOW_2 -> Icons.Rounded.Battery2Bar
            ALMOST_EMPTY_1 -> Icons.Rounded.Battery1Bar
            EMPTY_0 -> Icons.Rounded.Battery0Bar
        }
    }

    companion object {
        /**
         * Returns the correct Enum value depending on the given percentage.
         *
         * @return A [BatteryStatus] value corresponding to the given percentage.
         * @author Simone Robaldo
         */
        fun fromBatteryPercentage(percentage: Float): BatteryStatus {
            if(percentage >= 95) return FULL_7
            if(percentage >= 80) return ALMOST_FULL_6
            if(percentage >= 65) return ALMOST_MID_FULL_5
            if(percentage >= 45) return MID_4
            if(percentage >= 30) return ALMOST_LOW_3
            if(percentage >= 15) return LOW_2
            if(percentage >= 5) return ALMOST_EMPTY_1
            return EMPTY_0
        }
    }
}