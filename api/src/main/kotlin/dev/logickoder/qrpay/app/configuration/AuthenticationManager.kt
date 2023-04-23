package dev.logickoder.qrpay.app.configuration

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestHeader
import reactor.core.publisher.Mono

typealias Token = @receiver:RequestHeader(name = "Authorization") String

@Component
internal class AuthenticationManager(
    private val authorization: Authorization,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()
        val userId = authorization.getUserIdFromToken(authToken)

        return Mono.just(authorization.validateToken(authToken))
            .filter { it }
            .switchIfEmpty(Mono.empty())
            .map {
                val roles = (authorization.getAllClaimsFromToken(authToken).get(
                    "role",
                    MutableList::class.java
                ) ?: emptyList()).asSequence().map {
                    it as? String
                }.filterNot {
                    it.isNullOrBlank()
                }.map {
                    SimpleGrantedAuthority(it)
                }.toList()
                UsernamePasswordAuthenticationToken(userId, null, roles)
            }
    }
}