package letscode.sarafan.service

import letscode.sarafan.domain.*
import letscode.sarafan.dto.EventType
import letscode.sarafan.dto.ObjectType
import letscode.sarafan.util.WsSender
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import java.util.function.BiConsumer

@Service
class CommentService(private val commentRepo: CommentRepo, private val wsSender: WsSender) {

    private val commentSender: BiConsumer<EventType, Comment>
        get() = wsSender.getSender(ObjectType.Comment, Views.FullComment::class.java)

    fun create(comment: Comment, user: User): Comment {
        comment.author = user
        val commentFromDb = commentRepo.save(comment)

        commentSender.accept(EventType.Create, commentFromDb)

        return comment
    }
}