package dev.logickoder.qrpay.app.configuration

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class SecurityContextRepository(
    private val authenticationManager: AuthenticationManager,
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, sc: SecurityContext): Mono<Void> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter { authHeader: String ->
                authHeader.startsWith("Bearer ")
            }
            .flatMap { authHeader: String ->
                val authToken = authHeader.substring(7)
                val auth = UsernamePasswordAuthenticationToken(authToken, authToken)
                authenticationManager.authenticate(auth).map { authentication ->
                    SecurityContextImpl(authentication)
                }
            }
    }
}