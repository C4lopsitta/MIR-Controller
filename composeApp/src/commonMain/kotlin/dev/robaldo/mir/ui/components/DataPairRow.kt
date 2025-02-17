package dev.robaldo.mir.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DataPairRow(
    labelLeft: String,
    dataLeft: @Composable () -> Unit,
    labelRight: String,
    dataRight: @Composable () -> Unit
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding( vertical = 12.dp )
    ) {
        Column {
            Text( labelLeft )
            dataLeft()
        }
        Column (
            horizontalAlignment = Alignment.End
        ) {
            Text( labelRight )
            dataRight()
        }
    }
}

