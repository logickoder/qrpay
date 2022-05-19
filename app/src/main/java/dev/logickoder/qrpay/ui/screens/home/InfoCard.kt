package dev.logickoder.qrpay.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.composables.Icon
import dev.logickoder.qrpay.ui.shared.modifiers.nonExistent


@Composable
fun <T> InfoCard(
    @StringRes title: Int,
    content: T?,
    @StringRes caption: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier = modifier) {

    val padding = dimensionResource(id = R.dimen.secondary_padding) / 2
    val (titleView, iconView, contentView, captionView) = createRefs()

    Text(
        modifier = Modifier.constrainAs(titleView) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        },
        text = stringResource(id = title),
        style = MaterialTheme.typography.body2,
    )
    Icon(
        modifier = Modifier.constrainAs(iconView) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        },
        icon = icon,
    )
    Text(
        modifier = Modifier
            .constrainAs(contentView) {
                top.linkTo(iconView.bottom)
                start.linkTo(parent.start)
            }
            .nonExistent(content),
        text = content.toString(),
        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium),
        color = MaterialTheme.colors.secondaryVariant,
    )
    Text(
        modifier = Modifier.constrainAs(captionView) {
            top.linkTo(contentView.bottom, padding)
            start.linkTo(parent.start)
        },
        text = stringResource(id = caption),
        style = MaterialTheme.typography.caption,
    )
}
