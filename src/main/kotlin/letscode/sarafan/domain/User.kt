package letscode.sarafan.domain

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "usr")
class User(
        @Id
        val id: String,
        val name: String,
        val userpic: String,
        val email: String,
        val gender: String?,
        val locale: String
) {
        lateinit var lastVisit: LocalDateTime
}

interface UserDetailRepo : JpaRepository<User, String>