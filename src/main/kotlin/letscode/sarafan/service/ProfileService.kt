package letscode.sarafan.service

import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserDetailRepo
import letscode.sarafan.domain.UserSubscription
import org.springframework.stereotype.Service

@Service
class ProfileService(private val userDetailRepo: UserDetailRepo) {

    fun changeSubscription(channel: User, subscriber: User): User {
        val subscriptions = channel.subscribers.filter { subscription -> subscription.subscriber == subscriber }

        if (subscriptions.isEmpty()) {
            channel.subscribers.add(UserSubscription(channel, subscriber))
        } else {
            channel.subscribers.removeAll(subscriptions)
        }

        return userDetailRepo.save(channel)
    }

}
