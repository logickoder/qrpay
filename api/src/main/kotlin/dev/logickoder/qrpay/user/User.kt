package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.app.data.converter.BigDecimalSerializer
import dev.logickoder.qrpay.app.data.converter.RolesConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
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
@Entity(name = "user_")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("last_name")
    val lastName: String = "",
    val username: String = "",
    @Serializable(with = BigDecimalSerializer::class)
    val balance: BigDecimal = 50_000.toBigDecimal(),
    val password: String? = null,
    @Convert(converter = RolesConverter::class)
    val roles: List<Role> = listOf(Role.User),
)

enum class Role {
    User
}