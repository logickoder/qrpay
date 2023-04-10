package dev.logickoder.qrpay.app.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Autowired
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        service: ReactiveUserDetailsService,
    ): SecurityWebFilterChain {
        return http
            .csrf().disable()
//            .authorizeExchange()
//            .pathMatchers("/api/**").authenticated()
//            .pathMatchers("/api/login**", "/api/register**").permitAll()
//            .anyExchange().permitAll()
//            .and()
//            .httpBasic()
//            .and()
//            .authenticationManager(UserDetailsRepositoryReactiveAuthenticationManager(service))
            .build()
    }

//    @Bean
//    fun userDetailsService(): ReactiveUserDetailsService {
//        val userDetails = User.builder()
//            .username("user")
//            .password(passwordEncoder().encode("password"))
//            .roles("USER")
//            .build()
//        return MapReactiveUserDetailsService(userDetails)
//    }
}
