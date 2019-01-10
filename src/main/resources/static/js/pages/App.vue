<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-spacer></v-spacer>

            <span v-if="profile">
                <v-avatar :size="40" color="grey lighten-4">
                    <img :src="profile.userpic" alt="avatar">
                </v-avatar>

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
                if (data.objectType === 'Message') {
                    const index = this.messages.findIndex(item => item.id === data.body.id);
                    switch(data.eventType) {
                        case 'Create':
                        case 'Update':
                            if (index > -1) {
                                this.messages.splice(index, 1, data.body);
                            } else {
                                this.messages.push(data.body);
                            }
                            break;
                        case 'Remove':
                            if (index > -1) {
                                this.messages.splice(index, 1);
                            }
                            break;
                        default:
                            console.error(`Looks like the event type is unknown "${data.eventType}"`);
                    }
                } else {
                    console.error(`Looks like the object type is unknown "${data.objectType}"`);
                }
            });
        }
    }
</script>

<style>
</style>