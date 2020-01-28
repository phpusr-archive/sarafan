<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <user-link :user="message.author" size="48" />
            <div class="pt-3">
                <i>({{ message.id }})</i> {{ message.text }}
            </div>
        </v-card-text>
        <media v-if="message.link" :message="message" />
        <v-card-actions>
            <v-btn small flat round @click="edit">Edit</v-btn>
            <v-btn icon small @click="del">
              <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>

        <comment-list :message-id="message.id" :comments="message.comments" />
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'
    import Media from '../media/Media.vue'
    import CommentList from "../comment/CommentList.vue"
    import UserLink from "../UserLink.vue"

    export default {
        components: {CommentList, Media, UserLink},
        props: ['message', 'editMessage'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit () {
                this.editMessage(this.message)
            },
            del() {
                this.removeMessageAction(this.message);
            }
        }
    }
</script>

<style>

</style>
