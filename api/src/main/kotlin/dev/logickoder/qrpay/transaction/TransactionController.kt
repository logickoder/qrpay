package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.transaction.dto.SendMoneyRequest
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val service: TransactionService,
) {
    @PostMapping("/send-money")
    fun sendMoney(
        @RequestBody body: SendMoneyRequest,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
    ) = service.sendMoney(token, body)

    @GetMapping()
    fun getTransactions(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String
    ) = service.getTransactions(token)
}