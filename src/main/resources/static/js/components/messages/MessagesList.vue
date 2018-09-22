<template>
    <div style="position: relative; width: 300px;">
        <message-form :messages="messages" :messageAttr="message" />
        <message-row v-for="m in messages"
                     :key="m.id"
                     :message="m"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages" />
    </div>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'

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
                this.$resource('/message{/id}').remove({id: message.id}).then(result => {
                    if (result.ok) {
                        const index = this.messages.indexOf(message);
                        this.messages.splice(index, 1);
                    }
                })
            }
        }
    }
</script>

<style>

</style>