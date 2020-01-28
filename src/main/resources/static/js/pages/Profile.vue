<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex md6>
                <div class="title mb-3">User profile</div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex class="px-1">
                        <v-layout column>
                            <v-flex>{{profile.name}}</v-flex>
                            <v-flex>{{profile.locale}}</v-flex>
                            <v-flex>{{profile.gender}}</v-flex>
                            <v-flex>{{profile.lastVisit}}</v-flex>
                            <v-flex>
                                {{profile.subscriptions && profile.subscriptions.length}} subscriptions
                            </v-flex>
                            <v-flex>
                                <router-link v-if="isMyProfile" :to="`/subscriptions/${profile.id}`">
                                    {{profile.subscribers && profile.subscribers.length}} subscribers
                                </router-link>
                                <span v-else>
                                    {{profile.subscribers && profile.subscribers.length}} subscribers
                                </span>
                            </v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>

                <v-btn v-if="!isMyProfile" @click="changeSubscription">
                    {{isISubscribed ? 'Unsubscribe' : 'Subscribe'}}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from '../api/profile'
    import {mapActions} from 'vuex'

    export default {
        name: "Profile",
        data() {
            return {
                profile: {}
            }
        },
        computed: {
            isMyProfile() {
                const profileId = this.$route.params.id
                return !profileId || profileId === this.$store.state.profile.id
            },
            isISubscribed() {
                const subscribers = this.profile.subscribers
                return subscribers && subscribers.find(it => it.subscriber === this.$store.state.profile.id)
            }
        },
        methods: {
            ...mapActions(["reloadMessagesAction"]),
            async changeSubscription() {
                const data = await profileApi.changeSubscription(this.profile.id)
                this.profile = await data.json()
                this.reloadMessagesAction()
            },
            async updateProfile() {
                const id = this.$route.params.id || this.$store.state.profile.id
                const data = await profileApi.get(id)
                this.profile = await data.json()
                this.$forceUpdate()
            }
        },
        watch: {
            '$route'() {
                this.updateProfile()
            }
        },
        beforeMount() {
            this.updateProfile()
        }
    }
</script>

<style scoped>
</style>