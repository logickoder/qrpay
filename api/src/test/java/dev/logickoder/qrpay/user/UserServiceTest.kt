package dev.logickoder.qrpay.user


import dev.logickoder.qrpay.app.configuration.JwtToken
import dev.logickoder.qrpay.user.dto.LoginRequest
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserServiceTest {
    private lateinit var service: UserService

    @MockK
    private lateinit var jwtToken: JwtToken

    @MockK
    private lateinit var repository: UserRepository

    private val passwordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        service = UserService(repository, passwordEncoder, jwtToken)
    }

    @Test
    fun `should return error if user does not exist`() {
        // given
        every { repository.findByIdOrNull(any()) } returns null

        // when
        val responseEntity = service.getUser("test")
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
        every { repository.findByIdOrNull(any()) } returns User("test", "password")

        // when
        val responseEntity = service.getUser("test")
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
        every { repository.findByIdOrNull(any()) } returns User(username = "test")

        // when
        val responseEntity = service.createUser(User(username = "test"))
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
        every { repository.findByIdOrNull(any()) } returns null
        every { repository.save(any()) } returns User("test", "password")

        // when
        val responseEntity = service.createUser(User("test", "password"))
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
        every { repository.findByIdOrNull(any()) } returns User()

        // when
        val responseEntity = service.validateUser(LoginRequest("test", "password"))
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
            repository.findByIdOrNull(any())
        } returns User(
            password = passwordEncoder.encode("password")
        )
        every {
            jwtToken.generateToken(any())
        } returns ""

        // when
        val responseEntity = service.validateUser(LoginRequest("test", "password"))
        val response = responseEntity.body!!

        // then
        assertThat(responseEntity.statusCode, `is`(HttpStatus.OK))
        assertThat(response.success, `is`(true))
        assertThat(response.message, `is`("User validated successfully"))
        assertThat(response.data, `is`(notNullValue()))
    }

    @Test
    fun `test refreshToken with valid refresh token`() {
        // Mock input and expected output
        val refreshToken = "valid_refresh_token"
        val user = User(username = "testuser")
        every { jwtToken.getUsernameFromToken(refreshToken) } returns user.username
        every { repository.findByIdOrNull(user.username) } returns user
        every { jwtToken.generateToken(user) } returns "new_access_token"

        // Call the function
        val responseEntity = service.refreshToken(refreshToken)

        // Verify the response
        assertAll("Verify response entity",
            { Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode) },
            { Assertions.assertNotNull(responseEntity.body?.data) },
            {
                Assertions.assertEquals(
                    "Token refreshed successfully",
                    responseEntity.body?.message
                )
            }
        )

        // Verify the mock invocations
        verify(exactly = 1) { jwtToken.getUsernameFromToken(refreshToken) }
        verify(exactly = 1) { repository.findByIdOrNull(user.username) }
        verify(exactly = 1) { jwtToken.generateToken(user) }
        confirmVerified(jwtToken, repository)
    }

    @Test
    fun `test refreshToken with invalid refresh token`() {
        // Mock input and expected output
        val refreshToken = "invalid_refresh_token"
        every { jwtToken.getUsernameFromToken(refreshToken) } throws SecurityException("Invalid token")

        // Call the function
        val responseEntity = service.refreshToken(refreshToken)

        // Verify the response
        assertAll("Verify response entity",
            { Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.statusCode) },
            { Assertions.assertNull(responseEntity.body?.data) },
            { Assertions.assertNotNull(responseEntity.body?.message) }
        )

        // Verify the mock invocations
        verify(exactly = 1) { jwtToken.getUsernameFromToken(refreshToken) }
        confirmVerified(jwtToken, repository)
    }

    // Add more test cases for different scenarios as needed

    @AfterEach
    fun afterEach() {
        // Reset mocks after each test
        clearAllMocks()
    }
}
