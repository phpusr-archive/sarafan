<template>

    <v-layout>
        <v-text-field label="New message" placeholder="Write something" v-model="text" @keyup.enter.native="save" />
        <v-btn @click="save">Save</v-btn>
    </v-layout>

</template>

<script>

import messagesApi from "api/messages"

export default {

    props: ['messages', 'messageAttr'],
    data() {
        return {
            id: '', text: ''
        }
    },
    watch: {
        messageAttr(newVal)   {
            this.id = newVal.id;
            this.text = newVal.text;
        }
    },
    methods: {
        save() {
            const message = { id: this.id, text: this.text };

            if (this.id) {
                messagesApi.update(message)
                    .then(result => result.json())
                    .then(data => {
                        const index = this.messages.findIndex(item => item.id === data.id);
                        this.messages.splice(index, 1, data);
                    });
            } else {
                messagesApi.add(message)
                    .then(result => result.json())
                    .then(data => {
                        const index = this.messages.findIndex(item => item.id === data.id);
                        if (index > -1) {
                            this.messages.splice(index, 1, data);
                        } else {
                            this.messages.push(data);
                        }
                    });
            }

            this.text = '';
            this.id = '';
        }
    }
}
</script>

<style>

</style>