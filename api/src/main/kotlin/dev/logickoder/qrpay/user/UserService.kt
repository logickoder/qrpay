package dev.logickoder.qrpay.user


import dev.logickoder.qrpay.app.data.model.Response
import dev.logickoder.qrpay.user.dto.LoginRequest
import dev.logickoder.qrpay.user.dto.LoginResponse
import dev.logickoder.qrpay.user.dto.toLoginResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


/**
 * Service class that handles user-related business logic and interacts with the UserRepository.
 * @property repository the UserRepository used to interact with the database
 * @property passwordEncoder password encoder used to encode/decode passwords
 */
@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : ReactiveUserDetailsService {

    /**
     * Retrieves a user with the given username from the repository.
     * @param username the username of the user to retrieve
     * @return a Response object containing the retrieved user,
     * or an error message if the user doesn't exist
     */
    fun getUser(username: String): ResponseEntity<Response<LoginResponse?>> {
        return when (val user = repository.findByIdOrNull(username)) {
            null -> ResponseEntity(
                Response(null, "User does not exist", false),
                HttpStatus.NOT_FOUND,
            )

            else -> ResponseEntity.ok(
                Response(user.toLoginResponse(), "User retrieved successfully")
            )
        }
    }


    /**
     * Creates a new user with the given User object.
     * @param user the object representing the new user to create
     * @return a Response object containing the created user,
     * or an error message if the user already exists
     */
    fun createUser(user: User): ResponseEntity<Response<LoginResponse?>> {
        return when (repository.findByIdOrNull(user.username)) {
            // user does not exist
            null -> {
                // Hash the password before saving to the database
                val password = passwordEncoder.encode(user.password)
                repository.save(user.copy(password = password))
                ResponseEntity(
                    Response(user.toLoginResponse(), "User created successfully"),
                    HttpStatus.CREATED,
                )
            }
            // user already exists
            else -> ResponseEntity(
                Response(null, "User already exists", false),
                HttpStatus.CONFLICT,
            )
        }
    }

    /**
     * Validates a user with the given LoginRequest object.
     * @param request the LoginRequest object representing the user to validate
     * @return a Response object containing the validated user,
     * or an error message if the user doesn't exist
     * or the password is incorrect
     */
    fun validateUser(request: LoginRequest): ResponseEntity<Response<LoginResponse?>> {
        // Retrieve the user from the repository
        return when (val user = repository.findByIdOrNull(request.username)) {
            null -> ResponseEntity(
                Response(null, "User does not exist", false),
                HttpStatus.NOT_FOUND
            )

            else -> when {
                // Check if the passwords match
                passwordEncoder.matches(request.password, user.password) -> ResponseEntity.ok(
                    Response(
                        user.toLoginResponse(),
                        "User validated successfully"
                    )
                )

                else -> ResponseEntity(
                    Response(null, "Passwords don't match", false),
                    HttpStatus.UNAUTHORIZED,
                )
            }
        }
    }

    override fun findByUsername(username: String?): Mono<UserDetails> {
        val default = Mono.just(
            org.springframework.security.core.userdetails.User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build()
        )
        return when (username) {
            null -> default
            else -> when (val user = repository.findByIdOrNull(username)) {
                null -> default
                else -> Mono.just(user.toUserDetails())
            }
        }
    }
}