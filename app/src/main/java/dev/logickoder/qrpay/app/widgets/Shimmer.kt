package dev.logickoder.qrpay.app.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

fun <T> Modifier.nonExistent(content: T?): Modifier = this.then(
    Modifier.placeholder(
        visible = content == null,
        highlight = PlaceholderHighlight.shimmer(Color.White)
    )
)
