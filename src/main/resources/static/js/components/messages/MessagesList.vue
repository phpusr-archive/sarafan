<template>
    <div style="position: relative; width: 300px;">
        <message-form :messages="messages" :messageAttr="message" />
        <b-list-group>
            <message-row v-for="m in messages"
                         :key="m.id"
                         :message="m"
                         :editMessage="editMessage"
                         :deleteMessage="deleteMessage"
                         :messages="messages" />
        </b-list-group>
    </div>
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