<template>
    <v-layout align-space-around justify-start column>
        <message-form :messages="messages" :messageAttr="message" />
        <message-row v-for="m in sortedMessages"
                     :key="m.id"
                     :message="m"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages" />
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import {deleteMessage} from 'util/ws';

    export default {
        components: { MessageRow, MessageForm },
        props: ['messages'],
        data() {
            return { message: null }
        },
        computed: {
            sortedMessages() {
                return this.messages.sort((a, b) => -(a.id - b.id));
            }
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                deleteMessage(message.id)
            }
        }
    }
</script>

<style>

</style>