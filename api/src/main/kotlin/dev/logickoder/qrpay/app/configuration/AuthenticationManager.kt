package dev.logickoder.qrpay.app.configuration

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
internal class AuthenticationManager(
    private val authorization: Authorization,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()

        return Mono.just(
            authorization.validateToken(authToken) to authorization.getUserIdFromToken(authToken)
        ).filter { (validated, userId) ->
            validated && userId.isNotBlank()
        }.switchIfEmpty(
            Mono.empty()
        ).map { (_, userId) ->
            UsernamePasswordAuthenticationToken(
                userId,
                null,
                authorization.getRolesFromToken(authToken)
            )
        }
    }
}