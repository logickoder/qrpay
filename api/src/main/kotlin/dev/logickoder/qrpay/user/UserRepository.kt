package dev.logickoder.qrpay.user

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<User, String> {
    fun findByUsername(username: String): Optional<User>
}

fun UserRepository.findByUsernameOrNull(username: String): User? {
    return findByUsername(username).orElse(null)
}