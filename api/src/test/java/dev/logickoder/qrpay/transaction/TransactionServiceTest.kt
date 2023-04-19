package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.app.configuration.Authorization
import dev.logickoder.qrpay.app.configuration.Authorization.Companion.tokenFromAuth
import dev.logickoder.qrpay.transaction.dto.SendMoneyRequest
import dev.logickoder.qrpay.user.User
import dev.logickoder.qrpay.user.UserRepository
import dev.logickoder.qrpay.user.findByUsernameOrNull
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus

class TransactionServiceTest {

    private lateinit var authorization: Authorization
    private lateinit var userRepository: UserRepository
    private lateinit var repository: TransactionRepository
    private lateinit var json: Json
    private lateinit var transactionService: TransactionService

    @BeforeEach
    fun setUp() {
        authorization = mockk()
        userRepository = mockk()
        repository = mockk()
        json = mockk()
        transactionService = TransactionService(authorization, userRepository, repository, json)
    }

    @Test
    fun `sendMoney should return BAD_REQUEST when amount is 0`() {
        // Arrange
        val token = "token"
        val sendMoneyRequest = SendMoneyRequest("recipient", 0f, "narration")

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 0) { authorization.getUserIdFromToken(any()) }
        verify(exactly = 0) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { userRepository.findByUsernameOrNull(any()) }
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { userRepository.save(any()) }
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("No amount specified", result.body?.message)
        assertNull(result.body?.data)
    }

    @Test
    fun `sendMoney should return BAD_REQUEST when amount is negative`() {
        // Arrange
        val token = "token"
        val sendMoneyRequest = SendMoneyRequest("recipient", -100f, "narration")

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 0) { authorization.getUserIdFromToken(any()) }
        verify(exactly = 0) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { userRepository.findByUsernameOrNull(any()) }
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { userRepository.save(any()) }
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("Invalid amount specified", result.body?.message)
        assertNull(result.body?.data)
    }

    @Test
    fun `sendMoney should return UNAUTHORIZED when sender does not exist`() {
        // Arrange
        val token = "Bearer token"
        val sendMoneyRequest = SendMoneyRequest("recipient", 100f, "narration")
        every { authorization.getUserIdFromToken(token.tokenFromAuth()) } returns token
        every { userRepository.findByIdOrNull(any()) } returns null

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(token.tokenFromAuth()) }
        verify(exactly = 1) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { userRepository.findByUsernameOrNull(any()) }
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { userRepository.save(any()) }
        assertEquals(HttpStatus.UNAUTHORIZED, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("User does not exist", result.body?.message)
        assertNull(result.body?.data)
    }

    @Test
    fun `sendMoney should return NOT_FOUND when recipient does not exist`() {
        // Arrange
        val token = "Bearer token"
        val sendMoneyRequest = SendMoneyRequest("recipient", 100f, "narration")
        every { authorization.getUserIdFromToken(token.tokenFromAuth()) } returns token
        every { userRepository.findByIdOrNull(any()) } returns User()
        every { userRepository.findByUsernameOrNull(any()) } returns null

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(token.tokenFromAuth()) }
        verify(exactly = 1) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 1) { userRepository.findByUsernameOrNull(any()) }
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { userRepository.save(any()) }
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("Recipient does not exist", result.body?.message)
        assertNull(result.body?.data)
    }

    @Test
    fun `sendMoney should return BAD_REQUEST when sender is also recipient`() {
        // Arrange
        val token = "Bearer token"
        val user = User(username = "test", id = "12")
        val sendMoneyRequest = SendMoneyRequest(user.username, 100f, "narration")
        every { authorization.getUserIdFromToken(any()) } returns token
        every { userRepository.findByIdOrNull(any()) } returns user
        every { userRepository.findByUsernameOrNull(any()) } returns user

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(any()) }
        verify(exactly = 1) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 1) { userRepository.findByUsernameOrNull(any()) }
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { userRepository.save(any()) }
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("You cannot send money to yourself", result.body?.message)
        assertNull(result.body?.data)
    }

    @Test
    fun `sendMoney should return PAYMENT_REQUIRED when sender does not have sufficient balance`() {
        // Arrange
        val token = "Bearer token"
        val recipient = User("recipient")
        val sendMoneyRequest = SendMoneyRequest(recipient.username, 100f, "narration")
        every { authorization.getUserIdFromToken(any()) } returns token
        every { userRepository.findByIdOrNull(any()) } returns User(balance = 90.toBigDecimal())
        every { userRepository.findByUsernameOrNull(any()) } returns recipient

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(any()) }
        verify(exactly = 1) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 1) { userRepository.findByUsernameOrNull(any()) }
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { userRepository.save(any()) }
        assertEquals(HttpStatus.PAYMENT_REQUIRED, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("Insufficient balance", result.body?.message)
        assertNull(result.body?.data)
    }


    @Test
    fun `sendMoney should return OK when successful`() {
        // Arrange
        val token = "Bearer token"
        val sendMoneyRequest = SendMoneyRequest("2", 100f, "narration")
        every { authorization.getUserIdFromToken(any()) } returns "1"
        every { userRepository.findByIdOrNull("1") } returns User(id = "1")
        every { userRepository.findByUsernameOrNull("2") } returns User(id = "2")
        every { userRepository.save(any()) } answers { firstArg() }
        every { repository.save(any()) } answers { firstArg() }
        every { json.encodeToJsonElement<Transaction>(any()) } returns Json.encodeToJsonElement(
            Transaction()
        )

        // Act
        val result = transactionService.sendMoney(token, sendMoneyRequest)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(token.tokenFromAuth()) }
        verify(exactly = 1) { userRepository.findByIdOrNull("1") }
        verify(exactly = 1) { userRepository.findByUsernameOrNull("2") }
        verify(exactly = 2) { repository.save(any()) }
        verify(exactly = 2) { userRepository.save(any()) }
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(true, result.body?.success)
        assertEquals("Transaction completed successfully", result.body?.message)
        assertNotNull(result.body?.data)
    }

    @Test
    fun `getTransactions should return UNAUTHORIZED when user does not exist`() {
        // Arrange
        val token = "Bearer token"
        every { authorization.getUserIdFromToken(token.tokenFromAuth()) } returns token
        every { userRepository.findByIdOrNull(any()) } returns null

        // Act
        val result = transactionService.getTransactions(token)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(token.tokenFromAuth()) }
        verify(exactly = 1) { userRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { repository.findByUser(any()) }
        assertEquals(HttpStatus.UNAUTHORIZED, result.statusCode)
        assertEquals(false, result.body?.success)
        assertEquals("User does not exist", result.body?.message)
        assertNull(result.body?.data)
    }

    @Test
    fun `getTransactions should return OK when successful`() {
        // Arrange
        val auth = "Bearer token"
        val token = auth.tokenFromAuth()
        val user = User()
        val transactions = List(10) {
            Transaction(id = it.toString())
        }
        every { authorization.getUserIdFromToken(token) } returns user.id
        every { userRepository.findByIdOrNull(user.id) } returns user
        every { repository.findByUser(user) } returns transactions

        // Act
        val result = transactionService.getTransactions(auth)

        // Assert
        verify(exactly = 1) { authorization.getUserIdFromToken(token) }
        verify(exactly = 1) { userRepository.findByIdOrNull(user.id) }
        verify(exactly = 1) { repository.findByUser(user) }
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(true, result.body?.success)
        assertEquals("Transactions retrieved successfully", result.body?.message)
        assertEquals(transactions, result.body?.data)
    }
}