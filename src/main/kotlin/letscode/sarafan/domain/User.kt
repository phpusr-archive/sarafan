package letscode.sarafan.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "usr")
class User(
        @Id
        @JsonView(Views.IdName::class)
        val id: String,
        @JsonView(Views.IdName::class)
        val name: String,
        @JsonView(Views.IdName::class)
        val userpic: String,
        val email: String,
        val gender: String?,
        val locale: String
) : Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    lateinit var lastVisit: LocalDateTime
}

interface UserDetailRepo : JpaRepository<User, String>