package letscode.sarafan

import io.sentry.Sentry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SarafanApplication

fun main(args: Array<String>) {
    Sentry.capture("Application started")
    runApplication<SarafanApplication>(*args)
}
