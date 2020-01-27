package letscode.sarafan.controller

import com.fasterxml.jackson.databind.ObjectMapper
import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserDetailRepo
import letscode.sarafan.domain.Views
import letscode.sarafan.service.MessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainController(
        private val messageService: MessageService,
        private val userDetailRepo: UserDetailRepo,
        @Value("\${spring.profiles.active}")
        private val profile: String,
        mapper: ObjectMapper
) {

    private val config = mapper.setConfig(mapper.serializationConfig)
    private val messageWriter = config.writerWithView(Views.FullMessage::class.java)
    private val profileWriter = config.writerWithView(Views.FullProfile::class.java)

    @GetMapping
    fun main(
            model: Model,
            @AuthenticationPrincipal user: User?
    ): String {
        if (user != null) {
            val dbUser = userDetailRepo.findById(user.id).get()
            model["profile"] = profileWriter.writeValueAsString(dbUser)

            val sort = Sort.by(Sort.Direction.DESC, "id")
            val pageable = PageRequest.of(0, MessageController.MessagesPerPage, sort)
            val messagePageDto = messageService.list(pageable)
            val messages = messageWriter.writeValueAsString(messagePageDto.messages)
            model["messages"] = messages
            model["frontendData"] = mapOf(
                    "currentPage" to messagePageDto.currentPage,
                    "totalPages" to messagePageDto.totalPages
            )
        } else {
            model["messages"] = listOf<Any>()
            model["profile"] = "null"
            model["frontendData"] = mapOf<String, Any>()
        }
        model["isDevMode"] = profile == "dev"

        return "index"
    }

}