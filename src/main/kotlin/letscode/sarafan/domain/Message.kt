package letscode.sarafan.domain

import com.fasterxml.jackson.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
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

        @ManyToOne
        @JsonView(Views.FullMessage::class)
        var author: User?,

        @OneToMany(mappedBy = "message", orphanRemoval = true)
        @JsonView(Views.FullMessage::class)
        var comments: List<Comment>?,

        @JsonView(Views.FullMessage::class)
        var link: String?,

        @JsonView(Views.FullMessage::class)
        var linkTitle: String?,

        @JsonView(Views.FullMessage::class)
        var linkDescription: String?,

        @JsonView(Views.FullMessage::class)
        var linkCover: String?
)

interface MessageRepo : JpaRepository<Message, Long> {
    @EntityGraph(attributePaths = ["comments"])
    override fun findAll(pageable: Pageable): Page<Message>
}