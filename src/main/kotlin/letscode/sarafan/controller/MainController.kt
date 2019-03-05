package letscode.sarafan.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import letscode.sarafan.domain.MessageRepo
import letscode.sarafan.domain.User
import letscode.sarafan.domain.Views
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.io.BufferedWriter

@Controller
@RequestMapping("/")
class MainController(
        private val messageRepo: MessageRepo,
        @Value("\${spring.profiles.active}")
        private val profile: String,
        mapper: ObjectMapper
) {
    private val writer = mapper
            .setConfig(mapper.serializationConfig)
            .writerWithView(Views.FullMessage::class.java)

    @GetMapping
    fun main(
            model: Model,
            @AuthenticationPrincipal user: User?
    ): String {
        if (user != null) {
            val messages = writer.writeValueAsString(messageRepo.findAll())
            model["messages"] = messages
            model["frontendData"] = mapOf("profile" to user)
        } else {
            model["messages"] = listOf<Any>()
            model["frontendData"] = mapOf<String, Any>()
        }
        model["isDevMode"] = profile == "dev"

        return "index"
    }

}