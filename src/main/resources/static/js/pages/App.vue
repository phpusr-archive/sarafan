<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-spacer></v-spacer>

            <span v-if="profile">
                <b-img rounded="circle" width="40" height="40" blank-color="#777" alt="img" class="m-1" :src="profile.userpic" />

                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </span>
        </v-toolbar>
        <v-content>
            <v-container v-if="!profile">
                <span>Необходимо авторизоваться через: </span>
                <a href="/login">Google</a>
            </v-container>
            <v-container v-else>
                <messages-list :messages="messages" />
            </v-container>
        </v-content>

    </v-app>
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