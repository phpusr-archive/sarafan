package letscode.sarafan.domain

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.Embeddable
import java.io.Serializable

@Embeddable
class UserSubscriptionId(
        @JsonView(Views.Id::class)
        private val channelId: String,
        @JsonView(Views.Id::class)
        private val subscriberId: String
) : Serializable