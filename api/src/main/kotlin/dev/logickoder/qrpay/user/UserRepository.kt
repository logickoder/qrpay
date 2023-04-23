package dev.logickoder.qrpay.user

import org.springframework.data.repository.CrudRepository
import java.util.Optional

internal interface UserRepository : CrudRepository<UserEntity, String> {
    fun findByUsername(username: String): Optional<UserEntity>
}

internal fun UserRepository.findByUsernameOrNull(username: String): UserEntity? {
    return findByUsername(username).orElse(null)
}