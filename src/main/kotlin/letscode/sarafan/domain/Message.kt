package letscode.sarafan.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
data class Message(
        @Id
        @GeneratedValue
        @JsonView(Views.Id::class)
        val id: Long?,

        @JsonView(Views.IdName::class)
        var text: String?,

        @Column(updatable = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonView(Views.FullMessage::class)
        var creationDate: LocalDateTime?,

        @JsonView(Views.FullMessage::class)
        var link: String?,

        @JsonView(Views.FullMessage::class)
        var linkTitle: String?,

        @JsonView(Views.FullMessage::class)
        var linkDescription: String?,

        @JsonView(Views.FullMessage::class)
        var linkCover: String?
)

interface MessageRepo : JpaRepository<Message, Long>