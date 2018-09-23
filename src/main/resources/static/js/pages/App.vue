<template>
    <div class="p-5">
        <h3>Sarafan</h3>

        <div v-if="!profile">
            <span>Необходимо авторизоваться через: </span>
            <a href="/login">Google</a>
        </div>
        <div v-else>
            <div>
                <span>{{ profile.name }}</span>
                <a href="/logout">Log out</a>
            </div>
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