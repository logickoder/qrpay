package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.logickoder.qrpay.data.models.Transaction
import dev.logickoder.qrpay.data.models.User
import java.time.LocalDateTime

enum class HomeModal {
    SendMoney,
    ReceiveMoney,
    PaymentHistory
}

class HomeViewModel : ViewModel() {
    var balance by mutableStateOf(50000.0)
        private set

    var currency by mutableStateOf("$")
        private set

    val user by mutableStateOf(User("logickoder", "LOER22TS4A"))

    var transactions = mutableStateListOf(
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
        Transaction(LocalDateTime.now(), user, user, "h", 100.0),
        Transaction(LocalDateTime.now(), user, user, "h", -800.0),
    )
        private set
}
