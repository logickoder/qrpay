package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.components.Action
import dev.logickoder.qrpay.ui.shared.components.RadioBox
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.utils.Amount

enum class SendMethod {
    UserID,
    QR
}

@Composable
fun SendMoney(
    currency: String,
    amount: Amount,
    onAmountChange: (Amount) -> Unit,
    recipientsId: String,
    onRecipientsIdChange: (String) -> Unit,
    note: String,
    modifier: Modifier = Modifier,
    send: () -> Unit,
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
            style = Theme.typography.caption,
            color = Theme.colors.secondaryVariant,
            modifier = Modifier.padding(top = padding),
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
            value = recipientsId.uppercase(),
            onValueChange = { id -> onRecipientsIdChange(id) },
            placeholder = { Text(text = stringResource(id = R.string.recipients_user_id)) },
            singleLine = true,
            trailingIcon = { Text(text = "@${stringResource(id = R.string.app_name)}") }
        )
        Text(
            text = stringResource(id = R.string.userid_of_receiver),
            style = Theme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
            modifier = Modifier.padding(top = padding),
        )

        SMText(stringResource(id = R.string.amount))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = Theme.shapes.medium,
            value = amount.toString(),
            onValueChange = { amount -> onAmountChange(amount.toDoubleOrNull() ?: 0.0) },
            placeholder = { Text(text = stringResource(id = R.string.amount)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            leadingIcon = { Text(text = currency) }
        )

        SMText(stringResource(id = R.string.note))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            shape = Theme.shapes.medium,
            value = note,
            placeholder = { Text(text = stringResource(id = R.string.optional_note)) },
            onValueChange = {},
        )

        Button(
            onClick = send,
            modifier = Modifier
                .padding(vertical = padding / 2)
                .fillMaxWidth(),
            shape = Theme.shapes.medium,
        ) {
            Text(
                text = stringResource(id = R.string.send),
                style = Theme.typography.body2,
            )
        }
    }
}
