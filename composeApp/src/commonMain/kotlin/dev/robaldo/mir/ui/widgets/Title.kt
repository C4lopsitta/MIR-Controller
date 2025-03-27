package dev.robaldo.mir.ui.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em

/**
 * A simple title composable.
 *
 * @param text The text to be displayed.
 * @param modifier The modifier to be applied to the composable.
 *
 * @author Simone Robaldo
 */
@Composable
fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 4.em,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}
