package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.app.data.converter.RolesConverter
import dev.logickoder.qrpay.model.Role
import dev.logickoder.qrpay.model.User
import dev.logickoder.qrpay.model.dto.CreateUserRequest
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal

@Entity(name = "user_")
internal data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    @NotBlank(message = "Firstname is required")
    val firstname: String = "",
    @NotBlank(message = "Lastname is required")
    val lastname: String = "",
    @NotBlank(message = "Username is required")
    @Pattern(
        regexp = "^[a-zA-Z0-9_]{3,20}$",
        message = "Username must be 3-20 characters long and only contain letters, numbers, or underscores"
    )
    val username: String = "",
    val balance: BigDecimal = 50_000.toBigDecimal(),
    @NotBlank(message = "Password is required")
    val password: String? = null,
    @Convert(converter = RolesConverter::class)
    val roles: List<Role> = listOf(Role.User),
)

internal fun UserEntity.toUser() = User(
    id = id,
    firstname = firstname,
    lastname = lastname,
    username = username,
    balance = balance.toFloat(),
    password = null,
    roles = roles,
)

internal fun CreateUserRequest.toEntity() = UserEntity(
    firstname = firstname.trim(),
    lastname = lastname.trim(),
    username = username.trim(),
    password = password,
)