package dev.logickoder.qrpay.user

import dev.logickoder.qrpay.app.data.converter.SimpleGrantedAuthoritiesConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Serializable
@Entity(name = "user_")
data class User(
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("last_name")
    val lastName: String = "",
    @Id val username: String = "",
    val password: String = "",
    @Convert(converter = SimpleGrantedAuthoritiesConverter::class)
    @Transient val roles: List<SimpleGrantedAuthority> = emptyList(),
)

fun User.toUserDetails(): UserDetails {
    val user = this
    return object : UserDetails {
        override fun getAuthorities() = user.roles

        override fun getPassword() = user.password

        override fun getUsername() = user.username

        override fun isAccountNonExpired() = true

        override fun isAccountNonLocked() = true

        override fun isCredentialsNonExpired() = true

        override fun isEnabled() = true
    }
}