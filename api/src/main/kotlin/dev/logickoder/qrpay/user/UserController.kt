package dev.logickoder.qrpay.user

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
    fun getUser(@PathVariable username: String) = service.getUser(username)
}