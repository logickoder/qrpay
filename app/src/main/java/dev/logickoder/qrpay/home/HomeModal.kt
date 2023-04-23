package dev.logickoder.qrpay.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.logickoder.qrpay.model.Transaction
import dev.logickoder.qrpay.paymenthistory.PaymentHistory
import dev.logickoder.qrpay.receivemoney.ReceiveMoneyRoute
import dev.logickoder.qrpay.sendmoney.SendMoneyRoute
import kotlinx.collections.immutable.ImmutableList

enum class HomeModal {
    SendMoney,
    ReceiveMoney,
    PaymentHistory
}

@Composable
fun HomeModal(
    modal: HomeModal,
    currency: String,
    transactions: ImmutableList<Transaction>,
    modifier: Modifier = Modifier,
) {
    when (modal) {
        HomeModal.PaymentHistory -> PaymentHistory(
            transactions = transactions,
            currency = currency,
            modifier = modifier,
        )

        HomeModal.ReceiveMoney -> ReceiveMoneyRoute(
            modifier = modifier,
        )

        HomeModal.SendMoney -> SendMoneyRoute(
            modifier = modifier,
            currency = currency,
        )
    }
}