package dev.logickoder.qrpay.ui.shared.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropdownField(
    suggested: T,
    suggestions: List<T>,
    modifier: Modifier = Modifier,
    onSuggestionSelected: ((T) -> Unit),
    dropdownField: @Composable (String, Boolean) -> Unit = { suggestion, _ ->
        OutlinedTextField(
            value = suggestion,
            onValueChange = { }
        )
    }
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        dropdownField(suggested.toString(), expanded)
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
