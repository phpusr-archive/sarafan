<template>
    <div>
        <div v-if="!profile">
            <span>Необходимо авторизоваться через: </span>
            <a href="/login">Google</a>
        </div>
        <div v-else>
            <div>{{ profile.name }} <a href="/logout">Log out</a></div>
            <messages-list :messages="messages" />
        </div>
    </div>
</template>

<script>
    import MessagesList from 'components/messages/MessagesList.vue'

    export default {
        components: { MessagesList },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        }
    }
</script>

<style>

</style>