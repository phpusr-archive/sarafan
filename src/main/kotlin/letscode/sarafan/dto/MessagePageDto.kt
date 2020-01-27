package letscode.sarafan.dto

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.Message
import letscode.sarafan.domain.Views

@JsonView(Views.FullMessage::class)
class MessagePageDto(
        val messages: List<Message>,
        val currentPage: Int,
        val totalPages: Int
)