package letscode.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.Comment
import letscode.sarafan.domain.User
import letscode.sarafan.domain.Views
import letscode.sarafan.service.CommentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("comment")
class CommentController(private val commentService: CommentService) {

    @PostMapping
    @JsonView(Views.FullComment::class)
    fun create(
            @RequestBody comment: Comment,
            @AuthenticationPrincipal user: User
    ): Comment {
        return commentService.create(comment, user)
    }

}