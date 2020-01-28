package letscode.sarafan.config

import io.sentry.spring.SentryExceptionResolver
import io.sentry.spring.SentryServletContextInitializer
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver


@Configuration
class LogingConfig {
    @Bean
    fun sentryExceptionResolver(): HandlerExceptionResolver {
        return SentryExceptionResolver()
    }

    @Bean
    fun sentryServletContextInitializer(): ServletContextInitializer {
        return SentryServletContextInitializer()
    }
}