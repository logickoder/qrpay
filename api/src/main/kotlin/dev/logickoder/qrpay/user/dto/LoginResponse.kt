package dev.logickoder.qrpay.user.dto

import dev.logickoder.qrpay.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val username: String,
)

fun User.toLoginResponse() = LoginResponse(firstName, lastName, username)