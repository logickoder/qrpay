package dev.logickoder.qrpay.app.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RadioBox(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    padding: Dp = 8.dp,
    onClick: () -> Unit,
) = Row(
    modifier = modifier
        .border(
            if (selected) 2.dp else 1.dp,
            if (selected) color else unselectedColor,
            MaterialTheme.shapes.medium
        )
        .clickable { onClick() }
        .padding(vertical = padding / 2, horizontal = padding),
    verticalAlignment = Alignment.CenterVertically
) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
            unselectedColor = unselectedColor,
            selectedColor = color
        )
    )
    Spacer(modifier = Modifier.width(padding))
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )
}
