package dev.logickoder.qrpay.user


import dev.logickoder.qrpay.app.data.model.Response
import dev.logickoder.qrpay.app.utils.JwtUtil
import dev.logickoder.qrpay.user.dto.AuthResponse
import dev.logickoder.qrpay.user.dto.LoginRequest
import dev.logickoder.qrpay.user.dto.UserResponse
import dev.logickoder.qrpay.user.dto.toUserResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


/**
 * Service class that handles user-related business logic and interacts with the UserRepository.
 * @property repository the UserRepository used to interact with the database
 * @property passwordEncoder password encoder used to encode/decode passwords
 */
@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
) {

    /**
     * Retrieves a user with the given username from the repository.
     * @param username the username of the user to retrieve
     * @return a Response object containing the retrieved user,
     * or an error message if the user doesn't exist
     */
    fun getUser(username: String): ResponseEntity<Response<UserResponse?>> {
        return when (val user = repository.findByIdOrNull(username)) {
            null -> ResponseEntity(
                Response(null, "User does not exist", false),
                HttpStatus.NOT_FOUND,
            )

            else -> ResponseEntity.ok(
                Response(user.toUserResponse(), "User retrieved successfully")
            )
        }
    }


    /**
     * Creates a new user with the given User object.
     * @param user the object representing the new user to create
     * @return a Response object containing the created user,
     * or an error message if the user already exists
     */
    fun createUser(user: User): ResponseEntity<Response<UserResponse?>> {
        return when (repository.findByIdOrNull(user.username)) {
            // user does not exist
            null -> {
                // Hash the password before saving to the database
                val password = passwordEncoder.encode(user.password)
                repository.save(user.copy(password = password))
                ResponseEntity(
                    Response(user.toUserResponse(), "User created successfully"),
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
    fun validateUser(request: LoginRequest): ResponseEntity<Response<AuthResponse?>> {
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
                        AuthResponse(jwtUtil.generateToken(user)),
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
}