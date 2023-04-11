package dev.logickoder.qrpay.app.configuration

import dev.logickoder.qrpay.app.utils.JwtUtil
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class AuthenticationManager(
    private val jwtUtil: JwtUtil,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()
        val username = jwtUtil.getUsernameFromToken(authToken)

        return Mono.just(jwtUtil.validateToken(authToken))
            .filter { it }
            .switchIfEmpty(Mono.empty())
            .map {
                val roles = (jwtUtil.getAllClaimsFromToken(authToken).get(
                    "role",
                    MutableList::class.java
                ) ?: emptyList()).asSequence().map {
                    it as? String
                }.filterNot {
                    it.isNullOrBlank()
                }.map {
                    SimpleGrantedAuthority(it)
                }.toList()
                UsernamePasswordAuthenticationToken(username, null, roles)
            }
    }
}