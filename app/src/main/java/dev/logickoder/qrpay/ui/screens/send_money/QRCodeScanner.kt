package dev.logickoder.qrpay.ui.screens.send_money

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.model.qrCode
import dev.logickoder.qrpay.ui.theme.Theme


@Composable
fun QRCodeScanner(
    modifier: Modifier = Modifier,
    onCodeCaptured: (String) -> Unit,
) = Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (!result.contents.isNullOrBlank()) {
            onCodeCaptured(result.contents.qrCode.qrPayUid)
        }
    }
    val scanMessage = stringResource(id = R.string.qr_code_scan_prompt)
    val scanOptions = remember {
        ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt(scanMessage)
            setCameraId(0)
            setBeepEnabled(true)
            setBarcodeImageEnabled(true)
            setOrientationLocked(false)
        }
    }

    Button(
        onClick = { barcodeLauncher.launch(scanOptions) },
        shape = Theme.shapes.medium,
        content = {
            androidx.compose.material.Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.secondary_padding) / 2))
            Text(stringResource(id = R.string.start_scan))
        }
    )
}
