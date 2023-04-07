package dev.logickoder.qrpay.ui.screens.send_money

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.remote.ResultWrapper
import dev.logickoder.qrpay.ui.shared.composables.Action
import dev.logickoder.qrpay.ui.shared.composables.LoadingButton
import dev.logickoder.qrpay.ui.shared.composables.RadioBox
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.utils.formatted
import kotlin.math.absoluteValue

enum class SendMethod {
    UserID,
    QR
}

@Composable
fun SendMoney(
    currency: String,
    userId: String,
    modifier: Modifier = Modifier,
    viewModel: SendMoneyViewModel = viewModel()
) = Action(
    modifier = modifier,
    title = R.string.send_money,
) {
    val padding = dimensionResource(id = R.dimen.primary_padding)
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = padding)
    ) {

        @Composable
        fun SMText(text: String) = Text(
            text = text.uppercase(),
            style = Theme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            color = Theme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(
                top = padding,
                bottom = dimensionResource(R.dimen.secondary_padding) / 2
            ),
        )

        SMText(stringResource(id = R.string.send_method))
        var sendMethod by remember { mutableStateOf(SendMethod.UserID) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(padding)
        ) {
            SendMethod.values().forEach { method ->
                RadioBox(
                    selected = method == sendMethod,
                    text = method.name,
                    modifier = Modifier.weight(1f),
                    onClick = { sendMethod = method }
                )
            }
        }

        SMText(stringResource(id = R.string.recipients_id))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = Theme.shapes.medium,
            value = viewModel.recipientsId.uppercase(),
            onValueChange = { id -> viewModel.recipientsId = id },
            placeholder = { Text(text = stringResource(id = R.string.recipients_user_id)) },
            singleLine = true,
            textStyle = Theme.typography.bodyMedium,
            enabled = sendMethod == SendMethod.UserID,
            trailingIcon = {
                Text(
                    text = "@${stringResource(id = R.string.app_name)}",
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.primary_padding) / 2),
                )
            }
        )
        Text(
            text = stringResource(id = R.string.userid_of_receiver),
            style = Theme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(Theme.colorScheme.onSurfaceVariant.alpha),
            modifier = Modifier.padding(top = padding / 4),
        )

        if (sendMethod == SendMethod.QR) {
            SMText(stringResource(id = R.string.scan_qr_code))
            dev.logickoder.qrpay.ui.shared.composables.Card {
                QRCodeScanner(
                    modifier = Modifier.fillMaxWidth(),
                    onCodeCaptured = { id -> viewModel.recipientsId = id }
                )
            }
        }

        SMText(stringResource(id = R.string.amount))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = Theme.shapes.medium,
            value = viewModel.amount.formatted,
            textStyle = Theme.typography.bodyMedium,
            onValueChange = { amount ->
                amount.replace(",", "").toDoubleOrNull()?.let {
                    viewModel.amount = it.absoluteValue
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Text(currency) }
        )

        SMText(stringResource(id = R.string.note))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight * 2),
            shape = Theme.shapes.medium,
            value = viewModel.note,
            placeholder = { Text(text = stringResource(id = R.string.optional_note)) },
            onValueChange = { viewModel.note = it },
            textStyle = Theme.typography.bodyMedium,
        )

        viewModel.uiState?.let { state ->
            when (state) {
                is ResultWrapper.Success -> Text(
                    text = stringResource(id = R.string.transaction_successful),
                    style = Theme.typography.labelMedium,
                    color = Theme.colorScheme.secondary,
                    modifier = Modifier.padding(top = padding / 2),
                )
                is ResultWrapper.Failure -> if (state.error.message.toString().isNotBlank()) Text(
                    text = state.error.message.toString(),
                    style = Theme.typography.labelMedium,
                    color = Theme.colorScheme.error,
                    modifier = Modifier.padding(top = padding / 2),
                )
                else -> Unit
            }
        }

        LoadingButton(
            isLoading = viewModel.uiState == ResultWrapper.Loading,
            modifier = Modifier
                .padding(vertical = padding / 2)
                .fillMaxWidth(),
            enabled = viewModel.run {
                amount > 0 && note.isNotBlank() && recipientsId.length == userId.length
            },
            onClick = { viewModel.send(userId) },
            content = {
                Text(
                    text = stringResource(id = R.string.send),
                    style = Theme.typography.bodyLarge,
                )
            }
        )
    }
}
