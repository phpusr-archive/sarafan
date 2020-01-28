package letscode.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserSubscription
import letscode.sarafan.domain.Views
import letscode.sarafan.service.ProfileService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("profile")
class ProfileController(private val profileService: ProfileService) {

    @GetMapping("{id}")
    @JsonView(Views.FullProfile::class)
    fun get(@PathVariable("id") user: User): User {
        return user
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile::class)
    fun changeSubscription(
            @AuthenticationPrincipal subscriber: User,
            @PathVariable("channelId") channel: User
    ): User {
        if (subscriber == channel) {
            return channel
        }

        return profileService.changeSubscription(channel, subscriber)
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName::class)
    fun subscribers(@PathVariable("channelId") channel: User): List<UserSubscription> {
        return profileService.getSubscribers(channel)
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName::class)
    fun changeSubscriptionStatus(
            @AuthenticationPrincipal channel: User,
            @PathVariable("subscriberId") subscriber: User
    ): UserSubscription {
        return profileService.changeSubscriptionStatus(channel, subscriber)
    }

}