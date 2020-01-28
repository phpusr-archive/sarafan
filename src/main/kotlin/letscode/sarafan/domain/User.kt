package letscode.sarafan.domain

import com.fasterxml.jackson.annotation.*
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

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
        @JsonView(Views.FullProfile::class)
        val gender: String?,
        @JsonView(Views.FullProfile::class)
        val locale: String
) : Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullProfile::class)
    lateinit var lastVisit: LocalDateTime

    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    @JsonView(Views.FullProfile::class)
    val subscriptions: Set<UserSubscription> = setOf()

    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = [CascadeType.ALL]
    )
    @JsonView(Views.FullProfile::class)
    val subscribers: MutableSet<UserSubscription> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

interface UserDetailRepo : JpaRepository<User, String> {
    @EntityGraph(attributePaths=["subscriptions", "subscribers"])
    override fun findById(id: String): Optional<User>
}