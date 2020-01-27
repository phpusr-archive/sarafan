package letscode.sarafan.service

import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserDetailRepo
import org.springframework.stereotype.Service

@Service
class ProfileService(private val userDetailRepo: UserDetailRepo) {

    fun changeSubscription(channel: User, subscriber: User): User {
        val subscribers = channel.subscribers
        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber)
        } else {
            subscribers.add(subscriber)
        }

        return userDetailRepo.save(channel)
    }

}
