<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-btn v-if="profile" flat to="/">Messages</v-btn>
            <v-spacer></v-spacer>

            <span v-if="profile">
                <v-btn icon to="/user">
                    <v-avatar :size="40" color="grey lighten-4">
                        <img :src="profile.userpic" alt="avatar">
                    </v-avatar>
                </v-btn>

                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </span>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>

    </v-app>
</template>

<script>
    import {addHandler} from "util/ws";
    import {mapState, mapMutations} from 'vuex'

    export default {
        computed: mapState(['profile']),
        methods: mapMutations([
            'addMessageMutation',
            'updateMessageMutation',
            'removeMessageMutation',
            'addCommentMutation'
        ]),
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
                } else if (data.objectType === 'Comment') {
                    switch(data.eventType) {
                        case 'Create':
                            this.addCommentMutation(data.body);
                            break;
                        default:
                            console.error(`Looks like the event type is unknown "${data.eventType}"`);
                    }
                } else {
                    console.error(`Looks like the object type is unknown "${data.objectType}"`);
                }
            });
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>
</style>