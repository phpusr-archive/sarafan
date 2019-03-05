package letscode.sarafan.service

import letscode.sarafan.domain.Comment
import letscode.sarafan.domain.CommentRepo
import letscode.sarafan.domain.User
import org.springframework.stereotype.Service

@Service
class CommentService(private val commentRepo: CommentRepo) {
    fun create(comment: Comment, user: User): Comment {
        comment.author = user
        commentRepo.save(comment)

        return comment
    }
}