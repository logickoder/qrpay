package dev.logickoder.qrpay.user


import dev.logickoder.qrpay.user.dto.LoginRequest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserServiceTest {
    private lateinit var userService: UserService

    @MockK
    private lateinit var userRepository: UserRepository

    private val passwordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        userService = UserService(userRepository, passwordEncoder)
    }

    @Test
    fun `should return error if user does not exist`() {
        // given
        every { userRepository.findByIdOrNull(any()) } returns null

        // when
        val responseEntity = userService.getUser("test")
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.NOT_FOUND))
        assertThat(response.success, `is`(false))
        assertThat(response.message, `is`("User does not exist"))
        assertThat(response.data, `is`(nullValue()))
    }

    @Test
    fun `should return success if user exists`() {
        // given
        every { userRepository.findByIdOrNull(any()) } returns User("test", "password")

        // when
        val responseEntity = userService.getUser("test")
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.OK))
        assertThat(response.success, `is`(true))
        assertThat(response.message, `is`("User retrieved successfully"))
        assertThat(response.data, `is`(notNullValue()))
    }

    @Test
    fun `should return error if user already exists`() {
        // given
        every { userRepository.findByIdOrNull(any()) } returns User(username = "test")

        // when
        val responseEntity = userService.createUser(User(username = "test"))
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.CONFLICT))
        assertThat(response.success, `is`(false))
        assertThat(response.message, `is`("User already exists"))
        assertThat(response.data, `is`(nullValue()))
    }

    @Test
    fun `should create user and return success`() {
        // given
        every { userRepository.findByIdOrNull(any()) } returns null
        every { userRepository.save(any()) } returns User("test", "password")

        // when
        val responseEntity = userService.createUser(User("test", "password"))
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.CREATED))
        assertThat(response.success, `is`(true))
        assertThat(response.message, `is`("User created successfully"))
        assertThat(response.data, `is`(notNullValue()))
    }

    @Test
    fun `should return error if passwords don't match`() {
        // given
        every { userRepository.findByIdOrNull(any()) } returns User()

        // when
        val responseEntity = userService.validateUser(LoginRequest("test", "password"))
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.UNAUTHORIZED))
        assertThat(response.success, `is`(false))
        assertThat(response.message, `is`("Passwords don't match"))
        assertThat(response.data, `is`(nullValue()))
    }

    @Test
    fun `should validate user and return success`() {
        // given
        every {
            userRepository.findByIdOrNull(any())
        } returns User(
            password = passwordEncoder.encode("password")
        )

        // when
        val responseEntity = userService.validateUser(LoginRequest("test", "password"))
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.OK))
        assertThat(response.success, `is`(true))
        assertThat(response.message, `is`("User validated successfully"))
        assertThat(response.data, `is`(notNullValue()))
    }
}
