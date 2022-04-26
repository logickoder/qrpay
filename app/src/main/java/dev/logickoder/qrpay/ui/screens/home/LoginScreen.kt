package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.viewmodel.LoginScreenState
import dev.logickoder.qrpay.ui.shared.viewmodel.LoginViewModel
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
) = with(viewModel) {
    val login = loginScreenState == LoginScreenState.Login
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { /* Don't */ },
        title = {
            Text(
                text = stringResource(id = if (login) R.string.login else R.string.register),
                style = Theme.typography.h6,
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(id = if (login) R.string.user_id else R.string.name).uppercase(),
                    style = Theme.typography.caption.copy(fontWeight = FontWeight.Medium),
                    color = Theme.colors.secondaryVariant,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.secondary_padding) / 2
                    ),
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Theme.shapes.medium,
                    value = if (login) userId.uppercase() else name,
                    onValueChange = { id ->
                        if (login) userId = id else name = id
                    },
                    placeholder = {
                        Text(
                            text = stringResource(
                                id = if (login) R.string.enter_your_userid else R.string.name_or_nickname
                            )
                        )
                    },
                    singleLine = true,
                    textStyle = Theme.typography.body2,
                    leadingIcon = if (!login) {
                        {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null
                            )
                        }
                    } else null,
                    trailingIcon = if (login) {
                        {
                            Text(
                                text = "@${stringResource(id = R.string.app_name)}",
                                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.primary_padding) / 2),
                            )
                        }
                    } else null,
                )
                if (error.isNotEmpty()) Text(
                    text = error,
                    style = Theme.typography.caption,
                    color = Theme.colors.error,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.primary_padding) / 4),
                )
            }
        },
        buttons = {
            val padding = dimensionResource(R.dimen.primary_padding)
            Column(
                modifier = Modifier
                    .padding(horizontal = padding)
                    .padding(bottom = padding),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = stringResource(
                        id = if (login) R.string.dont_have_userid else R.string.already_have_userid,
                    ),
                    style = Theme.typography.body2.copy(fontWeight = FontWeight.Bold),
                    color = Theme.colors.primary,
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(R.dimen.primary_padding) / 2
                        )
                        .clickable {
                            if (!working)
                                loginScreenState =
                                    if (login) LoginScreenState.Register else LoginScreenState.Login
                        },
                )
                Button(
                    onClick = ::login,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = Theme.shapes.medium,
                    enabled = !working,
                ) {
                    Text(
                        text =
                        stringResource(id = if (login) R.string.login else R.string.register) +
                                if (working) "ing…" else "",
                        style = Theme.typography.body1,
                    )
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
        shape = Theme.shapes.large,
    )
}
