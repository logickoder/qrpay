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
    private fun Transaction.toResponse() = json.encodeToJsonElement(this).jsonObject.toMap().apply {
        Transaction.privateFields.forEach { field -> remove(field) }
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
                Response(null, "No amount specified", false),
                HttpStatus.BAD_REQUEST,
            )

            body.amount < 0f -> ResponseEntity(
                Response(null, "Invalid amount specified", false),
                HttpStatus.BAD_REQUEST,
            )

            else -> {
                // retrieve the user Id from the auth token
                val userId = authorization.getUserIdFromToken(Authorization.tokenFromAuth(token))

                var sender = userRepository.findByIdOrNull(userId)
                    ?: return ResponseEntity(
                        Response(null, "User does not exist", false),
                        HttpStatus.UNAUTHORIZED,
                    )

                var recipient = userRepository.findByUsernameOrNull(body.recipient)
                    ?: return ResponseEntity(
                        Response(null, "Recipient does not exist", false),
                        HttpStatus.NOT_FOUND,
                    )

                val amount = body.amount.toBigDecimal()
                // make sure the sender has enough balance to run this transaction
                if (amount > sender.balance) {
                    return ResponseEntity(
                        Response(null, "Insufficient balance", false),
                        HttpStatus.PAYMENT_REQUIRED,
                    )
                }

                // deduct the transaction amount from the sender
                sender = sender.copy(balance = sender.balance.minus(amount))
                val senderTransaction = Transaction(
                    description = body.narration,
                    amount = amount.negate(),
                    user = sender,
                )
                userRepository.save(sender)
                repository.save(senderTransaction)

                // add the transaction amount to the recipient
                recipient = recipient.copy(balance = recipient.balance.plus(amount))
                val recipientTransaction = Transaction(
                    description = body.narration,
                    amount = amount,
                    user = recipient,
                )
                userRepository.save(recipient)
                repository.save(recipientTransaction)

                ResponseEntity.ok(
                    Response(
                        senderTransaction.toResponse(),
                        "Transaction completed successfully",
                        true
                    )
                )
            }
        }
    }
}