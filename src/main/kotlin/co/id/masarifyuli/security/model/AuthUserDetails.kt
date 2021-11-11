package co.id.masarifyuli.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

interface AuthUserDetailsCompatible {
    fun toAuthUserDetails(roles :MutableList<String>): AuthUserDetails
}

class AuthUserDetails(private val username: String, private val password: String, private val role: MutableList<String>, private val isEnabled: Boolean) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        authorities.addAll(role.map { SimpleGrantedAuthority(it) })
        return authorities
    }

    override fun isEnabled(): Boolean = this.isEnabled
    override fun getUsername(): String = this.username
    override fun isCredentialsNonExpired(): Boolean = true
    override fun getPassword(): String = this.password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true

}