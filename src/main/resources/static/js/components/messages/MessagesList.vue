<template>
    <v-layout align-space-around justify-start column>
        <message-form :messageAttr="message" />
        <message-row v-for="m in sortedMessages"
                     :key="m.id"
                     :message="m"
                     :editMessage="editMessage" />
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import {mapGetters} from 'vuex'

    export default {
        components: { MessageRow, MessageForm },
        data() {
            return { message: null }
        },
        computed: mapGetters(['sortedMessages']),
        methods: {
            editMessage(message) {
                this.message = message
            }
        }
    }
</script>

<style>

</style>