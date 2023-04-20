package dev.logickoder.qrpay.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A REST controller for handling user-related requests.
 *
 * @param service The [UserService] instance to use for processing requests.
 */
@RestController
@RequestMapping("/api/users")
internal class UserController(
    private val service: UserService,
) {
    /**
     * Retrieves a user with the given [username].
     *
     * @param username The username of the user to retrieve.
     * @return The retrieved user.
     */
    @GetMapping("/{username}")
    @Operation(summary = "Get user", description = "Retrieves the specified user")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "401", description = "Unauthorized/non-existent user")
    fun getUser(@PathVariable username: String) = service.getUser(username)
}
