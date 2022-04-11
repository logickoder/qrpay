package dev.logickoder.qrpay.ui.shared.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.material.MaterialTheme as Theme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropdownField(
    suggested: T,
    suggestions: List<T>,
    modifier: Modifier = Modifier,
    onSuggestionSelected: ((T) -> Unit),
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextButton(
            onClick = { },
            border = BorderStroke(1.dp, Theme.colors.error),
            colors = ButtonDefaults.textButtonColors(contentColor = Theme.colors.error)
        ) {
            Text(suggested.toString())
            Icon(
                Icons.Filled.ArrowDropDown,
                "Trailing icon for exposed dropdown menu",
                Modifier.rotate(if (expanded) 180f else 360f)
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSuggestionSelected(suggestion)
                    }
                ) {
                    Text(text = suggestion.toString())
                }
            }
        }
    }
}