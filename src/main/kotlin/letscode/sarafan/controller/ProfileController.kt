package letscode.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import letscode.sarafan.domain.User
import letscode.sarafan.domain.UserDetailRepo
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

}