package dev.logickoder.qrpay.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import dev.logickoder.qrpay.R

@ReadOnlyComposable
@Composable
fun primaryPadding() = dimensionResource(id = R.dimen.primary_padding)

@ReadOnlyComposable
@Composable
fun secondaryPadding() = dimensionResource(id = R.dimen.secondary_padding)

@ReadOnlyComposable
@Composable
fun smallPadding() = secondaryPadding() / 2


@ReadOnlyComposable
@Composable
fun mediumPadding() = primaryPadding() / 2