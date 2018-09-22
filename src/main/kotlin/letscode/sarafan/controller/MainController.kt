package letscode.sarafan.controller

import letscode.sarafan.domain.MessageRepo
import letscode.sarafan.domain.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainController(private val messageRepo: MessageRepo) {

    @GetMapping
    fun main(
            model: Model,
            @AuthenticationPrincipal user: User?
    ): String {
        model["frontendData"] = mapOf("profile" to user, "messages" to messageRepo.findAll())
        return "index"
    }

}