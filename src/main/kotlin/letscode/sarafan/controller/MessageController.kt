package letscode.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.Message
import letscode.sarafan.domain.User
import letscode.sarafan.domain.Views
import letscode.sarafan.dto.MessagePageDto
import letscode.sarafan.service.MessageService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("message")
class MessageController(private val messageService: MessageService) {

    companion object {
        const val MessagesPerPage = 3
    }

    @GetMapping
    @JsonView(Views.FullMessage::class)
    fun list(
            @PageableDefault(size=MessagesPerPage, sort=["id"], direction= Sort.Direction.DESC) pageable: Pageable
    ): MessagePageDto {
        return messageService.list(pageable)
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun getOne(@PathVariable("id") message: Message?) = message

    @PostMapping
    @JsonView(Views.FullMessage::class)
    fun create(
            @RequestBody message: Message,
            @AuthenticationPrincipal user: User
    ): Message {
        return messageService.create(message, user)
    }

    @PutMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun update(
            @PathVariable("id") dbMessage: Message,
            @RequestBody message: Message
    ): Message {
        return messageService.update(dbMessage, message)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") message: Message) {
        messageService.delete(message)
    }


}