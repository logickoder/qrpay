package dev.logickoder.qrpay.sendmoney

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.mediumPadding
import dev.logickoder.qrpay.app.theme.primaryPadding
import dev.logickoder.qrpay.app.theme.smallPadding
import dev.logickoder.qrpay.app.utils.formatted
import dev.logickoder.qrpay.app.widgets.Action
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.InputField
import dev.logickoder.qrpay.app.widgets.InputFieldTitle
import dev.logickoder.qrpay.app.widgets.LoadingButton
import dev.logickoder.qrpay.app.widgets.RadioBox
import kotlin.math.absoluteValue

@Composable
fun SendMoneyScreen(
    state: SendMoneyState,
    currency: String,
    modifier: Modifier = Modifier,
    onUpdate: (SendMoneyState) -> Unit,
    onSend: () -> Unit,
) {
    Action(
        modifier = modifier,
        title = R.string.send_money,
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = primaryPadding()),
                content = {
                    InputFieldTitle(
                        text = stringResource(id = R.string.send_method),
                        modifier = Modifier.padding(
                            top = primaryPadding(),
                            bottom = smallPadding()
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(primaryPadding()),
                        content = {
                            SendMoneyMethod.values().forEach { method ->
                                RadioBox(
                                    selected = method == state.method,
                                    text = method.name,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        onUpdate(state.copy(method = method))
                                    }
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(primaryPadding()))

                    InputField(
                        title = stringResource(id = R.string.recipient),
                        value = state.recipient,
                        onValueChange = {
                            onUpdate(state.copy(recipient = it))
                        },
                        enabled = state.method == SendMoneyMethod.Username,
                        placeholder = stringResource(id = R.string.recipients_username),
                        trailingIcon = {
                            Text(
                                text = "@${stringResource(id = R.string.app_name)}",
                                modifier = Modifier.padding(end = mediumPadding()),
                            )
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.userid_of_receiver),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(MaterialTheme.colorScheme.onSurfaceVariant.alpha),
                        modifier = Modifier.padding(top = mediumPadding() / 2),
                    )

                    if (state.method == SendMoneyMethod.QR) {
                        InputFieldTitle(
                            text = stringResource(id = R.string.scan_qr_code),
                            modifier = Modifier.padding(
                                top = primaryPadding(),
                                bottom = smallPadding()
                            )
                        )
                        Card {
                            QRCodeScanner(
                                modifier = Modifier.fillMaxWidth(),
                                onCodeCaptured = {
                                    onUpdate(state.copy(recipient = it))
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(primaryPadding()))

                    InputField(
                        title = stringResource(id = R.string.amount),
                        value = state.amount.formatted,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        onValueChange = { amount ->
                            amount.replace(",", "").toFloatOrNull()?.let {
                                onUpdate(state.copy(amount = it.absoluteValue))
                            }
                        },
                        leadingIcon = {
                            Text(currency)
                        }
                    )

                    Spacer(modifier = Modifier.height(primaryPadding()))

                    InputField(
                        title = stringResource(id = R.string.note),
                        value = state.note,
                        height = TextFieldDefaults.MinHeight * 2,
                        singleLine = false,
                        onValueChange = {
                            onUpdate(state.copy(note = it))
                        },
                        placeholder = stringResource(id = R.string.optional_note)
                    )

                    if (state.apiResponse != null) {
                        Text(
                            text = if (state.apiResponse.success) {
                                stringResource(id = R.string.transaction_successful)
                            } else state.apiResponse.message,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (state.apiResponse.success) {
                                MaterialTheme.colorScheme.secondary
                            } else MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = primaryPadding() / 2),
                        )
                    }

                    LoadingButton(
                        isLoading = state.loading,
                        modifier = Modifier
                            .padding(vertical = mediumPadding())
                            .fillMaxWidth(),
                        enabled = state.enabled,
                        onClick = onSend,
                        content = {
                            Text(
                                text = stringResource(id = R.string.send),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    )
                }
            )
        }
    )
}
