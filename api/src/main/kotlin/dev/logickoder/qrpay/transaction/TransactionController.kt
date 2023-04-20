package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.model.dto.SendMoneyRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    @Operation(summary = "Send money", description = "Sends money to another user")
    @ApiResponse(responseCode = "200", description = "Successful transaction")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized/non-existent user")
    @ApiResponse(responseCode = "402", description = "Insufficient user balance")
    @ApiResponse(responseCode = "404", description = "Recipient not found")
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
    @Operation(
        summary = "Get transactions",
        description = "Retrieves all transactions for this user"
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "401", description = "Unauthorized/non-existent user")
    fun getTransactions(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String
    ) = service.getTransactions(token)
}
