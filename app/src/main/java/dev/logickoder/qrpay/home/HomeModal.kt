package dev.logickoder.qrpay.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import dev.logickoder.qrpay.app.data.model.Transaction
import dev.logickoder.qrpay.app.data.model.User
import dev.logickoder.qrpay.payment_history.PaymentHistory
import dev.logickoder.qrpay.receive_money.ReceiveMoney
import dev.logickoder.qrpay.send_money.SendMoney

enum class HomeModal {
    SendMoney,
    ReceiveMoney,
    PaymentHistory
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeModal(
    modal: HomeModal,
    user: State<User?>,
    transactions: List<Transaction>,
    modifier: Modifier = Modifier,
) {
    val id = user.value?.id.orEmpty()
    val currency = user.value?.currency.orEmpty()

    when (modal) {
        HomeModal.PaymentHistory -> PaymentHistory(
            transactions = transactions,
            currency = currency,
            modifier = modifier,
        )

        HomeModal.ReceiveMoney -> ReceiveMoney(
            userId = id
        )

        HomeModal.SendMoney -> SendMoney(
            userId = id,
            currency = currency,
        )
    }
}