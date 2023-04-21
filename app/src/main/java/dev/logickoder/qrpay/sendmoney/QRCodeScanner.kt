package dev.logickoder.qrpay.sendmoney

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.smallPadding


@Composable
fun QRCodeScanner(
    modifier: Modifier = Modifier,
    onCodeCaptured: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
                if (!result.contents.isNullOrBlank()) {
                    onCodeCaptured(result.contents)
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
                shape = MaterialTheme.shapes.medium,
                content = {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Outlined.CameraAlt,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(smallPadding()))
                    Text(stringResource(id = R.string.start_scan))
                }
            )
        }
    )
}
