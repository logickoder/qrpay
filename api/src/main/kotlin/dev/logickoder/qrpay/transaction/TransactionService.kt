package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.app.configuration.Authorization
import dev.logickoder.qrpay.app.data.model.Response
import dev.logickoder.qrpay.app.data.model.ResponseData
import dev.logickoder.qrpay.app.utils.toMap
import dev.logickoder.qrpay.transaction.dto.SendMoneyRequest
import dev.logickoder.qrpay.user.UserRepository
import dev.logickoder.qrpay.user.findByUsernameOrNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for handling transactions.
 */
@Service
class TransactionService(
    private val authorization: Authorization,
    private val userRepository: UserRepository,
    private val repository: TransactionRepository,
    private val json: Json,
) {
    /**
     * Extension function to convert Transaction object to JSON response.
     * @return JSON response as Map
     */
    private fun Transaction.toResponse(): ResponseData {
        // change all values that are not to be shown to null
        val transaction = copy(
            user = null
        )
        return json.encodeToJsonElement(transaction).jsonObject.toMap()
    }

    /**
     * Sends money from one user to another.
     * @param token Authentication token
     * @param body SendMoneyRequest object containing transaction details
     * @return ResponseEntity containing the response data
     */
    @Transactional
    fun sendMoney(token: String, body: SendMoneyRequest): ResponseEntity<Response<ResponseData?>> {
        return when {
            body.amount == 0f -> ResponseEntity(
                Response(false, "No amount specified", null),
                HttpStatus.BAD_REQUEST,
            )

            body.amount < 0f -> ResponseEntity(
                Response(false, "Invalid amount specified", null),
                HttpStatus.BAD_REQUEST,
            )

            else -> {
                // retrieve the user Id from the auth token
                val userId = authorization.getUserIdFromToken(Authorization.tokenFromAuth(token))

                var sender = userRepository.findByIdOrNull(userId)
                    ?: return ResponseEntity(
                        Response(false, "User does not exist", null),
                        HttpStatus.UNAUTHORIZED,
                    )

                var recipient = userRepository.findByUsernameOrNull(body.recipient)
                    ?: return ResponseEntity(
                        Response(false, "Recipient does not exist", null),
                        HttpStatus.NOT_FOUND,
                    )

                // make sure the recipient is not the same as the sender
                if (sender.id == recipient.id) return ResponseEntity(
                    Response(false, "You cannot send money to yourself", null),
                    HttpStatus.BAD_REQUEST,
                )

                val amount = body.amount.toBigDecimal()
                // make sure the sender has enough balance to run this transaction
                if (amount > sender.balance) {
                    return ResponseEntity(
                        Response(false, "Insufficient balance", null),
                        HttpStatus.PAYMENT_REQUIRED,
                    )
                }

                // deduct the transaction amount from the sender
                sender = sender.copy(balance = sender.balance.minus(amount))
                var senderTransaction = Transaction(
                    description = TransactionDescription(
                        value = body.narration,
                        recipient = recipient.username,
                    ),
                    amount = amount.negate(),
                    user = sender,
                )
                userRepository.save(sender)
                senderTransaction = repository.save(senderTransaction)

                // add the transaction amount to the recipient
                recipient = recipient.copy(balance = recipient.balance.plus(amount))
                val recipientTransaction = Transaction(
                    description = TransactionDescription(
                        value = body.narration,
                        sender = sender.username,
                    ),
                    amount = amount,
                    user = recipient,
                )
                userRepository.save(recipient)
                repository.save(recipientTransaction)

                ResponseEntity.ok(
                    Response(
                        true,
                        "Transaction completed successfully",
                        senderTransaction.toResponse(),
                    )
                )
            }
        }
    }

    /**
     * Retrieves transactions for a user with the given authentication token.
     *
     * @param token The authentication token of the user.
     * @return A [ResponseEntity] with a [Response] containing a list of [ResponseData] representing the transactions,
     *         along with the status and error messages.
     */
    fun getTransactions(token: String): ResponseEntity<Response<List<ResponseData>?>> {
        // Retrieve the user Id from the auth token
        val userId = authorization.getUserIdFromToken(Authorization.tokenFromAuth(token))

        val user = userRepository.findByIdOrNull(userId) ?: return ResponseEntity(
            Response(false, "User does not exist", null),
            HttpStatus.UNAUTHORIZED,
        )

        // Retrieve transactions for the user and map them to response objects
        val transactions = repository.findByUser(user).map { it.toResponse() }

        // Return the response entity with transactions and success status
        return ResponseEntity.ok(
            Response(true, "Transactions retrieved successfully", transactions)
        )
    }
}