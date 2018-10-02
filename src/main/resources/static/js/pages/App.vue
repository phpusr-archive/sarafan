<template>
    <div class="p-5">
        <h3>Sarafan</h3>

        <div v-if="!profile">
            <span>Необходимо авторизоваться через: </span>
            <a href="/login">Google</a>
        </div>
        <div v-else>
            <div>
                <b-img rounded="circle" width="75" height="75" blank-color="#777" alt="img" class="m-1" :src="profile.userpic" />
                <span>{{ profile.name }}</span>
                <a href="/logout">Log out</a>
            </div>
            <messages-list :messages="messages" />
        </div>
    </div>
</template>

<script>
    import MessagesList from 'components/messages/MessagesList.vue'
    import {addHandler} from "util/ws";
    import {getIndex, stompClient} from "util/collections"

    export default {
        components: { MessagesList },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler('/topic/activity', data => {
                const index = getIndex(this.messages, data.id);
                if (index > -1) {
                    this.messages.splice(index, 1, data);
                } else {
                    this.messages.push(data);
                }
            });
            addHandler('/topic/deleteMessage', messageId => {
                if (messageId >= 0) {
                    const index = getIndex(messageId);
                    this.messages.splice(index, 1);
                }
            });
        }
    }
</script>

<style>

</style>