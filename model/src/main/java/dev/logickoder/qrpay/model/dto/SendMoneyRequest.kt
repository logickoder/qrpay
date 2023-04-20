package dev.logickoder.qrpay.model.dto

import kotlinx.serialization.Serializable

/**
 * Data class representing a request to send money.
 *
 * @property recipient The username of the recipient of the money transfer.
 * @property amount The amount of money to be transferred.
 * @property narration The narration or description of the money transfer.
 */
@Serializable
data class SendMoneyRequest(
    val recipient: String,
    val amount: Float,
    val narration: String
)
