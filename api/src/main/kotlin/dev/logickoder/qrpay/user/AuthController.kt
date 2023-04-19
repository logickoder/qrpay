package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.user.dto.AuthResponse
import dev.logickoder.qrpay.user.dto.LoginRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
    @Operation(summary = "Create user", description = "Creates a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "409", description = "User already exists")
    fun createUser(@RequestBody body: User) = service.createUser(body)

    /**
     * Validates a user with the given [request].
     *
     * @param request The [LoginRequest] object representing the user to validate.
     * @return The validated [User] object.
     */
    @PostMapping("/login")
    @Operation(summary = "Validate user", description = "Validates an existing user")
    @ApiResponse(responseCode = "200", description = "User validated successfully")
    @ApiResponse(responseCode = "401", description = "Provided user credentials don't match")
    @ApiResponse(responseCode = "404", description = "User does not exist")
    fun validateUser(@RequestBody request: LoginRequest) = service.validateUser(request)

    /**
     * Generates a refresh token
     *
     * @param body The expired refresh token
     * @return The refreshed token.
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh user token", description = "Refreshes the jwt user token")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully")
    @ApiResponse(responseCode = "401", description = "User with specified token does not exist")
    @ApiResponse(responseCode = "403", description = "Failed to generate new token")
    fun refreshToken(@RequestBody body: AuthResponse) = service.refreshToken(body)
}
