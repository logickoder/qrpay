package dev.logickoder.qrpay.model

import kotlinx.serialization.Serializable

/**
 * User entity representing a user in the system.
 *
 * @property id The unique identifier for the user. Generated using UUID strategy.
 * @property firstname The firstname of the user.
 * @property lastname The lastname of the user.
 * @property username The username of the user.
 * @property balance The balance of the user. Default value is 50,000.
 * @property password The password of the user.
 * @property roles The roles assigned to the user. Default value is a list containing [Role.User].
 */
@Serializable
data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val username: String = "",
    val balance: Float = 50_000f,
    val password: String? = null,
    val roles: List<Role> = listOf(Role.User),
)

enum class Role {
    User
}