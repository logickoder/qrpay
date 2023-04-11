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
    val password: String = "",
    @Convert(converter = RolesConverter::class)
    val roles: List<Role> = listOf(Role.User),
) {
    companion object {
        val privateFields = arrayOf("id", "password")
    }
}

enum class Role {
    User
}