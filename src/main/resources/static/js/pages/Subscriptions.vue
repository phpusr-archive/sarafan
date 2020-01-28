<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list>
                <v-list-tile v-for="sub in subscribers">
                    <user-link :user="sub.subscriber" size="24" />

                    <v-btn @click="changeSubscriptionStatus(sub.subscriber.id)">
                        {{sub.active ? 'Dismiss': 'Approve'}}
                    </v-btn>
                </v-list-tile>
            </v-list>
        </v-layout>
    </v-container>
</template>

<script>
    import UserLink from "components/UserLink.vue"
    import profileApi from 'api/profile'

    export default {
        name: "Subscriptions",
        components: {UserLink},
        data() {
            return {
                subscribers: []
            }
        },
        methods: {
            async changeSubscriptionStatus(subscriberId) {
                const response = await profileApi.changeSubscriptionStatus(subscriberId)
                const data = await response.json()
                const subIndex = this.subscribers.findIndex(sub => sub.subscriber.id === subscriberId)
                const sub = this.subscribers[subIndex]
                sub.active = data.active
            }
        },
        async beforeMount() {
            const response = await profileApi.subscriberList(this.$store.state.profile.id)
            this.subscribers = await response.json()
        }
    }
</script>

<style scoped>

</style>