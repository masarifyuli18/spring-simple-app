package co.id.masarifyuli.security.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableResourceServer
class Oauth2ResourceServerConfig : ResourceServerConfigurerAdapter() {
    @Autowired
    lateinit var corsConfigurationSource: CorsConfigurationSource

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().configurationSource(corsConfigurationSource).and()
            .authorizeRequests()
            .antMatchers("/api/**").access("#oauth2.hasScope('read')")
            .antMatchers("/**").permitAll()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId("masarifyuli-resource")
        super.configure(resources)
    }
}