package dev.logickoder.qrpay.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import dev.logickoder.qrpay.R

@ReadOnlyComposable
@Composable
fun paddingPrimary() = dimensionResource(id = R.dimen.primary_padding)

@ReadOnlyComposable
@Composable
fun paddingSecondary() = dimensionResource(id = R.dimen.secondary_padding)

@ReadOnlyComposable
@Composable
fun paddingSmall() = paddingSecondary() / 2


@ReadOnlyComposable
@Composable
fun paddingMedium() = paddingPrimary() / 2