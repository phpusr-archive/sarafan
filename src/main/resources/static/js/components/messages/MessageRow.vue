<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <div>
                <v-avatar v-if="message.author && message.author.userpic" size="40px">
                    <img :src="message.author.userpic">
                </v-avatar>
                <v-avatar v-else color="indigo" size="36px">
                    <v-icon dark>account_circle</v-icon>
                </v-avatar>
                <span class="ml-2">{{ authorName }}</span>
            </div>
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

    export default {
        components: {CommentList, Media},
        props: ['message', 'editMessage'],
        computed: {
            authorName() {
                return this.message.author ? this.message.author.name : 'unknown'
            }
        },
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
