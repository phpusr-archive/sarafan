package letscode.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.Message
import letscode.sarafan.domain.MessageRepo
import letscode.sarafan.domain.Views
import letscode.sarafan.dto.EventType
import letscode.sarafan.dto.ObjectType
import letscode.sarafan.util.WsSender
import org.springframework.beans.BeanUtils
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.function.BiConsumer

@RestController
@RequestMapping("message")
class MessageController(
        private val messageRepo: MessageRepo,
        private val wsSender: WsSender
) {

    private val messageSender: BiConsumer<EventType, Message>
        get() = wsSender.getSender(ObjectType.Message, Views.IdName::class.java)

    @GetMapping
    @JsonView(Views.IdName::class)
    fun list() = messageRepo.findAll()

    @GetMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun getOne(@PathVariable("id") message: Message?) = message

    @PostMapping
    @JsonView(Views.IdName::class)
    fun create(@RequestBody message: Message): Message {
        message.creationDate = LocalDateTime.now()
        val createdMessage = messageRepo.save(message)
        messageSender.accept(EventType.Create, createdMessage)

        return createdMessage
    }

    @PutMapping("{id}")
    @JsonView(Views.IdName::class)
    fun update(
            @PathVariable("id") dbMessage: Message,
            @RequestBody message: Message
    ): Message {
        BeanUtils.copyProperties(message, dbMessage, "id")
        val updatedMessage = messageRepo.save(dbMessage)
        messageSender.accept(EventType.Update, updatedMessage)

        return updatedMessage
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") message: Message) {
        messageRepo.delete(message)
        messageSender.accept(EventType.Remove, message)
    }

}