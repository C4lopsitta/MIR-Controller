package dev.robaldo.mir.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun BatteryPill(batteryPercentage: Float) {
    val clampedPercentage = batteryPercentage.coerceIn(0f, 100f)
    val fraction = clampedPercentage / 100f



    Box (
        modifier = Modifier
            .height(24.dp)
            .width(48.dp)
            .clip(RoundedCornerShape(50))
            .background(androidx.compose.material3.MaterialTheme.colorScheme.onSurface)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Text(
                "${batteryPercentage.roundToInt()}",
                fontWeight = FontWeight.Bold,
                color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
            )
        }

        Box (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction)
                .background(androidx.compose.material3.MaterialTheme.colorScheme.primary)
                .clip(RoundedCornerShape(50))
        )
    }
}
