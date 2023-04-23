package dev.logickoder.qrpay.auth

data class AuthScreenState(
    val username: String = "",
    val usernameError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val firstname: String = "",
    val firstnameError: Int? = null,
    val lastname: String = "",
    val lastnameError: Int? = null,
    val type: AuthScreenType = AuthScreenType.Login,
    val enabled: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
)
