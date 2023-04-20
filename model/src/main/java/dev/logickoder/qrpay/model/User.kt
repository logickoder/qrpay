package dev.logickoder.qrpay.model

import dev.logickoder.qrpay.model.serializer.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

/**
 * User entity representing a user in the system.
 *
 * @property id The unique identifier for the user. Generated using UUID strategy.
 * @property firstName The first name of the user.
 * @property lastName The last name of the user.
 * @property username The username of the user.
 * @property balance The balance of the user. Default value is 50,000.
 * @property password The password of the user.
 * @property roles The roles assigned to the user. Default value is a list containing [Role.User].
 */
@Serializable
data class User(
    val id: String = "",
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("last_name")
    val lastName: String = "",
    val username: String = "",
    @Serializable(with = BigDecimalSerializer::class)
    val balance: BigDecimal = BigDecimal.ZERO,
    val password: String? = null,
    val roles: List<Role> = listOf(Role.User),
)

enum class Role {
    User
}