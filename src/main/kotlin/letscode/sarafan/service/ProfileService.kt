package letscode.sarafan.service

import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserDetailRepo
import letscode.sarafan.domain.UserSubscription
import letscode.sarafan.domain.UserSubscriptionRepo
import org.springframework.stereotype.Service

@Service
class ProfileService(
        private val userDetailRepo: UserDetailRepo,
        private val userSubscriptionRepo: UserSubscriptionRepo
) {

    fun changeSubscription(channel: User, subscriber: User): User {
        val subscriptions = channel.subscribers.filter { subscription -> subscription.subscriber == subscriber }

        if (subscriptions.isEmpty()) {
            channel.subscribers.add(UserSubscription(channel, subscriber))
        } else {
            channel.subscribers.removeAll(subscriptions)
        }

        return userDetailRepo.save(channel)
    }

    fun getSubscribers(channel: User): List<UserSubscription> {
        return userSubscriptionRepo.findByChannel(channel)
    }

    fun changeSubscriptionStatus(channel: User, subscriber: User): UserSubscription {
        val subscription = userSubscriptionRepo.findByChannelAndSubscriber(channel, subscriber)
        subscription.active = !subscription.active

        return userSubscriptionRepo.save(subscription)
    }

}
