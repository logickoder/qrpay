package dev.logickoder.qrpay.app.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import dev.logickoder.qrpay.app.theme.smallPadding

@Composable
fun InputField(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    height: Dp = TextFieldDefaults.MinHeight,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        content = {
            InputFieldTitle(text = title)
            Spacer(modifier = Modifier.height(smallPadding()))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height),
                shape = MaterialTheme.shapes.medium,
                value = value,
                onValueChange = onValueChange,
                placeholder = if (placeholder != null) {
                    {
                        Text(text = placeholder)
                    }
                } else null,
                enabled = enabled,
                singleLine = singleLine,
                textStyle = MaterialTheme.typography.bodyMedium,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
            )
        }
    )
}


@Composable
fun InputFieldTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text.uppercase(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
    )
}