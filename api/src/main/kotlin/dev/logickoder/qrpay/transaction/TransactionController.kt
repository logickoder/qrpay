package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.model.dto.SendMoneyRequest
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

/**
 * Controller for handling transaction-related operations.
 *
 * @property service TransactionService instance for handling transaction operations.
 */
@RestController
@RequestMapping("/api/transactions")
internal class TransactionController(
    private val service: TransactionService,
) {
    /**
     * Endpoint for sending money.
     *
     * @param body SendMoneyRequest object containing the request body.
     * @param token Authorization token passed as a request header.
     * @return Response from the TransactionService after sending money.
     */
    @PostMapping("/send-money")
    fun sendMoney(
        @RequestBody body: SendMoneyRequest,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
    ) = service.sendMoney(token, body)

    /**
     * Endpoint for getting transactions.
     *
     * @param token Authorization token passed as a request header.
     * @return Response from the TransactionService containing transactions.
     */
    @GetMapping()
    fun getTransactions(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String
    ) = service.getTransactions(token)
}
