package dev.logickoder.qrpay.ui.shared.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun RadioBox(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Theme.colors.primary,
    unselectedColor: Color = Theme.colors.onSurface.copy(alpha = 0.6f),
    padding: Dp = 8.dp,
    onClick: () -> Unit,
) = Row(
    modifier = modifier
        .border(
            1.dp,
            if (selected) color else unselectedColor,
            Theme.shapes.medium
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
        style = Theme.typography.body2,
        color = Theme.colors.onBackground,
    )
}
