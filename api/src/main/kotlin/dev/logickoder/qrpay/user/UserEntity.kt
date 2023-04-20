package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.app.data.converter.RolesConverter
import dev.logickoder.qrpay.model.Role
import dev.logickoder.qrpay.model.User
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity(name = "user_")
internal data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val balance: BigDecimal = 50_000.toBigDecimal(),
    val password: String? = null,
    @Convert(converter = RolesConverter::class)
    val roles: List<Role> = listOf(Role.User),
)

internal fun UserEntity.toUser() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    balance = balance,
    password = password,
    roles = roles,
)

internal fun User.toEntity() = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    balance = balance,
    password = password,
    roles = roles,
)