package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.logickoder.qrpay.data.models.User


class HomeViewModel : ViewModel() {
    var balance by mutableStateOf(50000.0)
        private set

    var currency by mutableStateOf("$")
        private set

    val user by mutableStateOf(User("logickoder", "LOER22TS4A"))

    var transactions by mutableStateOf(1)
        private set
}
