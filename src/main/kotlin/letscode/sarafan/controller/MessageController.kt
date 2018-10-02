package letscode.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.Message
import letscode.sarafan.domain.MessageRepo
import letscode.sarafan.domain.Views
import org.springframework.beans.BeanUtils
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("message")
class MessageController(private val messageRepo: MessageRepo) {

    @GetMapping
    @JsonView(Views.IdName::class)
    fun list() = messageRepo.findAll()

    @GetMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun getOne(@PathVariable("id") message: Message?) = message

    @PostMapping
    fun create(@RequestBody message: Message): Message {
        message.creationDate = LocalDateTime.now()
        return messageRepo.save(message)
    }

    @PutMapping("{id}")
    fun update(
            @PathVariable("id") dbMessage: Message,
            @RequestBody message: Message
    ): Message {
        BeanUtils.copyProperties(message, dbMessage, "id")
        return messageRepo.save(dbMessage)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") message: Message): String {
        messageRepo.delete(message);
        return "OK"
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    fun change(message: Message): Message {
        return messageRepo.save(message)
    }

}