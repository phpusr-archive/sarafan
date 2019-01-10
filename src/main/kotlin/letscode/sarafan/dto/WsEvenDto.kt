package letscode.sarafan.dto

import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.Views

@JsonView(Views.Id::class)
class WsEvenDto(
        val objectType: ObjectType,
        val eventType: EventType,
        @JsonRawValue
        val body: String
)