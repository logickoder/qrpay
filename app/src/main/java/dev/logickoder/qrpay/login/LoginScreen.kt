package dev.logickoder.qrpay.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.app.widgets.Error
import dev.logickoder.qrpay.app.widgets.LoadingButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
) = with(viewModel) {
    AlertDialog(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.primary_padding))
            .clip(Theme.shapes.large),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = { /* Don't */ },
        content = {
            if (uiState.isError.not()) Text(
                text = stringResource(id = if (uiState.isLogin) R.string.login else R.string.register),
                style = Theme.typography.headlineSmall,
            )
            if (uiState.isError) Error(
                error = uiState.value,
                modifier = Modifier.fillMaxWidth()
            ) else Column {
                Text(
                    text = stringResource(id = if (uiState.isLogin) R.string.user_id else R.string.name).uppercase(),
                    style = Theme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = Theme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.secondary_padding) / 2
                    ),
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Theme.shapes.medium,
                    value = uiState.value,
                    onValueChange = { value ->
                        uiState.value = value
                    },
                    placeholder = {
                        Text(
                            text = stringResource(
                                id = if (uiState.isLogin) R.string.enter_your_userid else R.string.name_or_nickname
                            )
                        )
                    },
                    singleLine = true,
                    textStyle = Theme.typography.bodyMedium,
                    leadingIcon = if (uiState.isLogin.not()) {
                        {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null
                            )
                        }
                    } else null,
                    trailingIcon = if (uiState.isLogin) {
                        {
                            Text(
                                text = "@${stringResource(id = R.string.app_name)}",
                                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.primary_padding) / 2),
                            )
                        }
                    } else null,
                )
            }
            val padding = dimensionResource(R.dimen.primary_padding)
            Column(
                modifier = Modifier
                    .padding(horizontal = padding)
                    .padding(bottom = padding),
                horizontalAlignment = Alignment.End,
            ) {
                if (uiState.isError.not()) Text(
                    text = stringResource(
                        id = if (uiState.isLogin) R.string.dont_have_userid else R.string.already_have_userid,
                    ),
                    style = Theme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Theme.colorScheme.primary,
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(R.dimen.primary_padding) / 2
                        )
                        .clickable {
                            if (!working)
                                switchScreen(
                                    if (uiState.isLogin) LoginScreenState.Register
                                    else LoginScreenState.Login
                                )
                        },
                )
                LoadingButton(
                    isLoading = working,
                    onClick = ::buttonClick,
                    modifier = Modifier.fillMaxWidth(),
                    color = if (uiState.isError) Theme.colorScheme.error else null,
                    content = {
                        Text(
                            text = stringResource(
                                when (uiState) {
                                    LoginScreenState.Login -> R.string.login
                                    LoginScreenState.Register -> R.string.register
                                    LoginScreenState.Error -> R.string.go_back
                                }
                            ),
                            style = Theme.typography.bodyLarge,
                        )
                    }
                )
            }
        },
    )
}
