package co.id.masarifyuli.security.service


import co.id.masarifyuli.entity.EmployeeRepository
import co.id.masarifyuli.security.model.AuthUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class AuthUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var employeeListRepository: EmployeeRepository

    override fun loadUserByUsername(username: String): UserDetails {
        println("loadUserByUsername")
        println("username: $username")
        var ud: AuthUserDetails? = null
        val user = employeeListRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("Wrong username or password") }
        ud = user.toAuthUserDetails(mutableListOf())
        AccountStatusUserDetailsChecker().check(ud)
        return ud
    }

}