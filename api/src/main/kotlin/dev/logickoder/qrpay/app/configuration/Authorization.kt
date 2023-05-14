package dev.logickoder.qrpay.app.configuration

import dev.logickoder.qrpay.user.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
internal class Authorization {
    @Value("\${qrpay.authorization.secret}")
    private lateinit var secret: String

    @Value("\${qrpay.authorization.expiration}")
    private lateinit var expirationTime: String

    private lateinit var key: Key

    @PostConstruct
    fun init() {
        key = Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun getUserIdFromToken(token: String?): String {
        return getAllClaimsFromToken(token)?.subject.orEmpty()
    }

    fun getRolesFromToken(token: String?): List<SimpleGrantedAuthority> {
        return (getAllClaimsFromToken(token)?.get(
            "role",
            MutableList::class.java
        ) ?: emptyList()).asSequence().map {
            it as? String
        }.filterNot {
            it.isNullOrBlank()
        }.map {
            SimpleGrantedAuthority(it)
        }.toList()
    }

    fun validateToken(token: String): Boolean {
        // retrieve expiration date from token
        val expiration = getAllClaimsFromToken(token)?.expiration ?: return false
        // check if the expiration date is before now
        return !expiration.before(Date())
    }

    fun generateToken(user: UserEntity): String {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["role"] = user.roles
        val expirationTimeLong = expirationTime.toLong() //in second
        val createdDate = Date()
        val expirationDate = Date(createdDate.time + expirationTimeLong * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.id)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    private fun getAllClaimsFromToken(token: String?): Claims? {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        fun String.tokenFromAuth() = split(Regex("\\s+")).lastOrNull() ?: ""
    }
}