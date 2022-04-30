package dev.logickoder.qrpay.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.component.Action
import dev.logickoder.qrpay.ui.shared.component.RadioBox
import dev.logickoder.qrpay.ui.shared.viewmodel.SendMoneyViewModel
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.utils.ResultWrapper
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
            style = Theme.typography.caption.copy(fontWeight = FontWeight.Medium),
            color = Theme.colors.secondaryVariant,
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
            textStyle = Theme.typography.body2,
            trailingIcon = {
                Text(
                    text = "@${stringResource(id = R.string.app_name)}",
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.primary_padding) / 2),
                )
            }
        )
        Text(
            text = stringResource(id = R.string.userid_of_receiver),
            style = Theme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
            modifier = Modifier.padding(top = padding / 4),
        )

        SMText(stringResource(id = R.string.amount))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = Theme.shapes.medium,
            value = viewModel.amount.formatted,
            textStyle = Theme.typography.body2,
            onValueChange = { amount ->
                amount.replace(",", "").toDoubleOrNull()?.let {
                    viewModel.amount = it.absoluteValue
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Text(
                    text = currency,
                )
            }
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
            textStyle = Theme.typography.body2,
        )

        viewModel.uiState?.let { state ->
            when (state) {
                is ResultWrapper.Success -> Text(
                    text = stringResource(id = R.string.transaction_successful),
                    style = Theme.typography.caption,
                    color = Theme.colors.secondary,
                    modifier = Modifier.padding(top = padding / 2),
                )
                is ResultWrapper.Failure -> if (state.error.message.toString().isNotBlank()) Text(
                    text = state.error.message.toString(),
                    style = Theme.typography.caption,
                    color = Theme.colors.error,
                    modifier = Modifier.padding(top = padding / 2),
                )
                else -> Unit
            }
        }

        Button(
            onClick = { viewModel.send(userId) },
            modifier = Modifier
                .padding(vertical = padding / 2)
                .fillMaxWidth(),
            shape = Theme.shapes.medium,
            enabled = viewModel.run {
                amount > 0 && note.isNotBlank() && recipientsId.length == userId.length &&
                        uiState != ResultWrapper.Loading
            }
        ) {
            Text(
                text = stringResource(id = R.string.send),
                style = Theme.typography.body1,
            )
        }
    }
}
