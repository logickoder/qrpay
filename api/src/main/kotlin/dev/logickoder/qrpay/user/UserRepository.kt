package dev.logickoder.qrpay.user

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String>