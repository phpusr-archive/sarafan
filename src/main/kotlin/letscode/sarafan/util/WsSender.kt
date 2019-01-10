package letscode.sarafan.util

import com.fasterxml.jackson.databind.ObjectMapper
import letscode.sarafan.dto.EventType
import letscode.sarafan.dto.ObjectType
import letscode.sarafan.dto.WsEvenDto
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.util.function.BiConsumer
import java.util.function.Consumer

@Component
class WsSender(
        private val template: SimpMessagingTemplate,
        private val mapper: ObjectMapper
) {

    fun <T> getSender(objectType: ObjectType, view: Class<*>): BiConsumer<EventType, T> {
        val writer = mapper.setConfig(mapper.serializationConfig).writerWithView(view)
        return BiConsumer { eventType: EventType, payload: T ->
            val value = writer.writeValueAsString(payload)
            template.convertAndSend("/topic/activity", WsEvenDto(objectType, eventType, value))
        }
    }

}