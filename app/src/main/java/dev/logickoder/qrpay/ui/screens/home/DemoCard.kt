package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.modifiers.nonExistent
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.formattedWith


@Composable
fun DemoCard(
    userName: String?,
    demoBalance: Amount?,
    currency: String?,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier = modifier) {

    val verticalPadding = dimensionResource(id = R.dimen.secondary_padding)
    val horizontalPadding = dimensionResource(id = R.dimen.primary_padding)

    val (left, right) = createRefs()

    Column(modifier = Modifier.constrainAs(left) {
        top.linkTo(parent.top)
        linkTo(start = parent.start, end = right.start, endMargin = horizontalPadding / 2)
        width = Dimension.fillToConstraints
    }) {
        Text(
            modifier = Modifier.nonExistent(userName),
            text = stringResource(id = R.string.demo_title, userName.toString()),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colors.secondaryVariant,
        )
        Text(
            text = stringResource(id = R.string.demo_subtitle),
            style = MaterialTheme.typography.caption,
        )
        Text(
            modifier = Modifier
                .padding(vertical = verticalPadding / 2)
                .nonExistent(demoBalance),
            text = demoBalance?.formattedWith(currency.toString()).toString(),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colors.primary,
        )
        Button(shape = MaterialTheme.shapes.medium, onClick = {}) {
            Text(text = stringResource(id = R.string.view_history))
        }
    }

    Image(
        modifier = Modifier
            .offset(y = horizontalPadding - 2.dp)
            .constrainAs(right) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
        painter = painterResource(id = R.drawable.prize_light),
        contentDescription = null,
    )
}
