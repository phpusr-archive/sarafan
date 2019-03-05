package letscode.sarafan.domain

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.*

@Entity
class Comment(
        @Id
        @GeneratedValue
        @JsonView(Views.IdName::class)
        val id: Long?,

        @JsonView(Views.IdName::class)
        val text: String,

        @ManyToOne
        @JoinColumn(name = "message_id")
        val message: Message,

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false, updatable = false)
        @JsonView(Views.FullMessage::class)
        var author: User?
)

interface CommentRepo : JpaRepository<Comment, Long>