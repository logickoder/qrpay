package dev.logickoder.qrpay.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.paddingSecondary

@Composable
fun ActionCard(
    @DrawableRes imageId: Int,
    @StringRes textId: Int,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    Image(
        modifier = Modifier.size(dimensionResource(id = R.dimen.action_image_size)),
        painter = painterResource(id = imageId),
        contentDescription = null,
    )
    Spacer(modifier = Modifier.height(paddingSecondary()))
    Text(
        text = stringResource(id = textId),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
    )
}
