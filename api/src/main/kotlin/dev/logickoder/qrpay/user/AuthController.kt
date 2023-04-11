package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.user.dto.LoginRequest
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
@RequestMapping("api")
class AuthController(
    private val service: UserService,
) {
    /**
     * Creates a new user with the given [body].
     *
     * @param body The [User] object representing the new user to create.
     * @return The created [User] object.
     */
    @PostMapping("/register")
    fun createUser(@RequestBody body: User) = service.createUser(body)

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
     * @param token The expired refresh token
     * @return The refreshed token.
     */
    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody token: String) = service.refreshToken(token)
}
