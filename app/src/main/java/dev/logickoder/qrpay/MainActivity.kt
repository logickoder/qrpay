package dev.logickoder.qrpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import dev.logickoder.qrpay.ui.screens.home.HomeScreen
import dev.logickoder.qrpay.ui.theme.QRPayTheme
import dev.logickoder.qrpay.ui.theme.Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRPayTheme {
                MainContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QRPayTheme {
        MainContent()
    }
}

@Composable
fun MainContent() = Surface(
    modifier = Modifier.fillMaxSize(),
    color = Theme.colors.background
) {
    HomeScreen()
}
