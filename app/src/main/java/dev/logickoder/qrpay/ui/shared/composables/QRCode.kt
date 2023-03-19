package dev.logickoder.qrpay.ui.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.model.QrCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun QRCode(
    qrCode: QrCode,
    modifier: Modifier = Modifier,
) = BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
    val size = (maxWidth.value / 1.5).toInt()
    val bitmap = remember {
        try {
            BarcodeEncoder().encodeBitmap(
                Json.encodeToString(qrCode),
                BarcodeFormat.QR_CODE,
                size,
                size
            )
                .asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }
    if (bitmap != null) Image(
        bitmap = bitmap,
        contentDescription = stringResource(id = R.string.qr_code_description, qrCode.qrPayUid),
        modifier = Modifier.size(size.dp)
    )
}
