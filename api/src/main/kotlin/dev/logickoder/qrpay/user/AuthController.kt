package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.model.User
import dev.logickoder.qrpay.model.dto.AuthResponse
import dev.logickoder.qrpay.model.dto.CreateUserRequest
import dev.logickoder.qrpay.model.dto.LoginRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A REST controller for handling auth-related requests.
 *
 * @param service The [UserService] instance to use for processing requests.
 */
@RestController
@RequestMapping("/api")
internal class AuthController(
    private val service: UserService,
) {
    /**
     * Creates a new user with the given [body].
     *
     * @param body The [CreateUserRequest] object representing the new user to create.
     * @return The created [User] object.
     */
    @PostMapping("/register")
    fun createUser(@RequestBody body: CreateUserRequest) = service.createUser(body)

    /**
     * Validates a user with the given [request].
     *
     * @param request The [LoginRequest] object representing the user to validate.
     * @return The validated [User] object.
     */
    @PostMapping("/login")
    fun validateUser(@RequestBody request: LoginRequest) = service.validateUser(request)

    /**
     * Generates a refresh token
     *
     * @param body The expired refresh token
     * @return The refreshed token.
     */
    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody body: AuthResponse) = service.refreshToken(body)
}
