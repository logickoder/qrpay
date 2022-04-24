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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.component.Action
import dev.logickoder.qrpay.ui.shared.component.RadioBox
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.formatted

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
    onNoteChange: (String) -> Unit,
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
            value = recipientsId.uppercase(),
            onValueChange = { id -> onRecipientsIdChange(id) },
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
            value = amount.formatted,
            textStyle = Theme.typography.body2,
            onValueChange = { amount ->
                amount.replace(",", "").toDoubleOrNull()?.let { onAmountChange(it) }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Text(text = currency) }
        )

        SMText(stringResource(id = R.string.note))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight * 2),
            shape = Theme.shapes.medium,
            value = note,
            placeholder = { Text(text = stringResource(id = R.string.optional_note)) },
            onValueChange = onNoteChange,
            textStyle = Theme.typography.body2,
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
                style = Theme.typography.body1,
            )
        }
    }
}
