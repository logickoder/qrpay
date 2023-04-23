package dev.logickoder.qrpay.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.window.DialogProperties
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.mediumPadding
import dev.logickoder.qrpay.app.theme.primaryPadding
import dev.logickoder.qrpay.app.theme.smallPadding
import dev.logickoder.qrpay.app.widgets.InputField
import dev.logickoder.qrpay.app.widgets.LoadingButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    state: AuthScreenState,
    modifier: Modifier = Modifier,
    update: (AuthScreenState) -> Unit,
    onClick: () -> Unit,
) {
    AlertDialog(
        modifier = modifier
            .padding(primaryPadding())
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .padding(primaryPadding()),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = { /* Don't */ },
        content = {
            Column(
                content = {
                    when (state.error) {
                        null -> {
                            if (state.type == AuthScreenType.Register) {
                                InputField(
                                    title = stringResource(R.string.firstname),
                                    value = state.firstname,
                                    onValueChange = {
                                        update(state.copy(firstname = it.trim()))
                                    },
                                    placeholder = stringResource(R.string.enter_firstname),
                                )
                                Spacer(modifier = Modifier.height(mediumPadding()))
                                InputField(
                                    title = stringResource(R.string.lastname),
                                    value = state.lastname,
                                    onValueChange = {
                                        update(state.copy(lastname = it.trim()))
                                    },
                                    placeholder = stringResource(R.string.enter_lastname),
                                )
                                Spacer(modifier = Modifier.height(mediumPadding()))
                            }

                            InputField(
                                title = stringResource(R.string.username),
                                value = state.username,
                                onValueChange = {
                                    update(state.copy(username = it.trim()))
                                },
                                placeholder = stringResource(R.string.enter_username),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = null
                                    )
                                },
                            )

                            Spacer(modifier = Modifier.height(mediumPadding()))

                            val passwordVisible = remember {
                                mutableStateOf(false)
                            }
                            InputField(
                                title = stringResource(R.string.password),
                                value = state.password,
                                onValueChange = {
                                    update(state.copy(password = it))
                                },
                                placeholder = stringResource(R.string.enter_password),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            passwordVisible.value = !passwordVisible.value
                                        },
                                        content = {
                                            Icon(
                                                imageVector = if (passwordVisible.value) {
                                                    Icons.Outlined.VisibilityOff
                                                } else Icons.Outlined.Visibility,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                },
                                visualTransformation = if (passwordVisible.value) {
                                    VisualTransformation.None
                                } else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done,
                                )
                            )

                            Spacer(modifier = Modifier.height(smallPadding()))

                            Row {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = stringResource(
                                        id = if (state.type == AuthScreenType.Login) {
                                            R.string.dont_have_account
                                        } else R.string.already_have_account,
                                    ),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.clickable {
                                        if (!state.loading) {
                                            update(
                                                state.copy(
                                                    type = when (state.type) {
                                                        AuthScreenType.Login -> AuthScreenType.Register
                                                        AuthScreenType.Register -> AuthScreenType.Login
                                                    }
                                                )
                                            )
                                        }
                                    },
                                )
                            }
                        }

                        else -> {
                            AuthErrorScreen(
                                modifier = Modifier.fillMaxWidth(),
                                error = state.error
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(primaryPadding()))

                    LoadingButton(
                        isLoading = state.loading,
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth(),
                        color = if (state.error != null) MaterialTheme.colorScheme.error else null,
                        content = {
                            Text(
                                text = stringResource(
                                    when {
                                        state.error != null -> R.string.go_back
                                        else -> when (state.type) {
                                            AuthScreenType.Login -> R.string.login
                                            AuthScreenType.Register -> R.string.register
                                        }
                                    }
                                ),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    )
                }
            )
        },
    )
}
