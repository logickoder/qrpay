package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.user.dto.LoginRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A REST controller for handling user-related requests.
 *
 * @param service The [UserService] instance to use for processing requests.
 */
@RestController
@RequestMapping("api/users")
class UserController(
    private val service: UserService,
) {
    /**
     * Retrieves a user with the given [username].
     *
     * @param username The username of the user to retrieve.
     * @return The retrieved [User] object.
     */
    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String) = service.getUser(username)

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
}
