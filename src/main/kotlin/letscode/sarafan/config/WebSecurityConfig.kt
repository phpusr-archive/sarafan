package letscode.sarafan.config

import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserDetailRepo
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import java.time.LocalDateTime
import java.util.function.Supplier

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
    }

    @Bean
    fun principalExtractor(userDetailRepo: UserDetailRepo): PrincipalExtractor {
        return PrincipalExtractor { map ->
            val id = map["sub"] as String
            val user = userDetailRepo.findById(id).orElseGet {
                User(
                        id = id,
                        name = map["name"] as String,
                        userpic = map["picture"] as String,
                        email = map["email"] as String,
                        gender = map["gender"] as String?,
                        locale = map["locale"] as String
                )
            }
            user.lastVisit = LocalDateTime.now()
            userDetailRepo.save(user)
        }
    }

}