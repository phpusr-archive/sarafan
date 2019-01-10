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
                <messages-list />
            </v-container>
        </v-content>

    </v-app>
</template>

<script>
    import MessagesList from 'components/messages/MessagesList.vue'
    import {addHandler} from "util/ws";
    import {mapState, mapMutations} from 'vuex'

    export default {
        components: { MessagesList },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created() {
            addHandler('/topic/activity', data => {
                if (data.objectType === 'Message') {
                    switch(data.eventType) {
                        case 'Create':
                            this.addMessageMutation(data.body);
                            break;
                        case 'Update':
                            this.updateMessageMutation(data.body);
                            break;
                        case 'Remove':
                            this.removeMessageMutation(data.body);
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