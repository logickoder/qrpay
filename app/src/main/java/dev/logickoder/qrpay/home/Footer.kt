package dev.logickoder.qrpay.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.app.theme.paddingPrimary
import dev.logickoder.qrpay.app.theme.paddingSecondary
import dev.logickoder.qrpay.app.widgets.DropdownField

@Composable
fun Footer(
    currencies: List<String>,
    onCurrencyChange: (String) -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
) = QRPayTheme(
    // make the footer be an invert of the normal color
    darkTheme = !isSystemInDarkTheme(),
    content = {
        Column(
            modifier = modifier
                .background(Theme.colorScheme.background.copy(alpha = 0.1f))
                .fillMaxWidth()
                .padding(
                    vertical = paddingSecondary(),
                    horizontal = paddingPrimary(),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                val text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append(stringResource(id = R.string.app_name))
                    }
                    withStyle(style = SpanStyle(color = Theme.colorScheme.onTertiary)) {
                        append(" ${stringResource(id = R.string.made_by)} ")
                    }
                    val me = stringResource(id = R.string.me)
                    pushStringAnnotation(tag = "me", annotation = "https://github.com/$me")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) { append(me) }
                    pop()
                }

                val context = LocalContext.current
                ClickableText(
                    text = text,
                    style = Theme.typography.bodyMedium.copy(color = Theme.colorScheme.onSurface),
                    onClick = { offset ->
                        text.getStringAnnotations(tag = "me", start = offset, end = offset)
                            .firstOrNull()
                            ?.let {
                                startActivity(
                                    context,
                                    Intent(Intent.ACTION_VIEW, Uri.parse(it.item)),
                                    null
                                )
                            }
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(horizontal = paddingPrimary()),
                    content = {
                        DropdownField(
                            suggested = stringResource(id = R.string.currency),
                            suggestions = currencies,
                            onSuggestionSelected = onCurrencyChange
                        ) { suggested, expanded ->
                            TextButton(
                                onClick = {},
                                shape = Theme.shapes.medium,
                                border = BorderStroke(1.dp, Theme.colorScheme.onBackground),
                                colors = ButtonDefaults.textButtonColors(contentColor = Theme.colorScheme.onBackground),
                                content = {
                                    Text(
                                        text = suggested,
                                        style = Theme.typography.labelMedium,
                                    )
                                    Icon(
                                        Icons.Outlined.ArrowDropDown,
                                        "Trailing icon for exposed dropdown menu",
                                        Modifier.rotate(if (expanded) 180f else 360f)
                                    )
                                }
                            )
                        }
                        Spacer(Modifier.width(dimensionResource(id = R.dimen.primary_padding)))
                        TextButton(
                            onClick = logout,
                            shape = Theme.shapes.medium,
                            border = BorderStroke(1.dp, Theme.colorScheme.error),
                            colors = ButtonDefaults.textButtonColors(contentColor = Theme.colorScheme.error),
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.Login,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(180f),
                                )
                                Text(
                                    text = stringResource(id = R.string.logout),
                                    style = Theme.typography.labelMedium,
                                )
                            }
                        )
                    }
                )
            }
        )
    }
)

@Composable
@Preview(showBackground = true)
private fun FooterPreview() = Footer(currencies = listOf("$"), onCurrencyChange = {}, logout = {})