package dev.logickoder.qrpay.ui.shared.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Login
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun Footer(
    currencies: List<String>,
    onCurrencyChange: (String) -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .background(Theme.colors.onError)
        .fillMaxWidth()
        .padding(
            vertical = dimensionResource(id = R.dimen.secondary_padding),
            horizontal = dimensionResource(id = R.dimen.primary_padding),
        ),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
            append(stringResource(id = R.string.app_name))
        }
        withStyle(style = SpanStyle(color = Theme.colors.onSecondary)) {
            append(" ${stringResource(id = R.string.made_by)} ")
        }
        val me = stringResource(id = R.string.me)
        pushStringAnnotation(tag = "me", annotation = "https://github.com/$me")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) { append(me) }
        pop()
    }

    ClickableText(
        text = text,
        style = Theme.typography.body1,
        onClick = { offset ->
            text.getStringAnnotations(tag = "me", start = offset, end = offset).firstOrNull()?.let {
                Log.d("my url", it.item)
            }
        })
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.primary_padding))
    ) {
        DropdownField(
            suggested = stringResource(id = R.string.currency),
            suggestions = currencies,
            onSuggestionSelected = onCurrencyChange
        )
        Spacer(Modifier.width(dimensionResource(id = R.dimen.primary_padding)))
        TextButton(
            onClick = logout,
            border = BorderStroke(1.dp, Theme.colors.error),
            colors = ButtonDefaults.textButtonColors(contentColor = Theme.colors.error)
        ) {
            Icon(
                imageVector = Icons.Outlined.Login,
                contentDescription = null,
                modifier = Modifier.rotate(180f),
            )
            Text(text = stringResource(id = R.string.logout))
        }
    }
}
