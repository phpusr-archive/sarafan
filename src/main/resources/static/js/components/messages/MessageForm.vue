<template>

    <v-layout>
        <v-text-field label="New message" placeholder="Write something" v-model="text" @keyup.enter.native="save" />
        <v-btn @click="save">Save</v-btn>
    </v-layout>

</template>

<script>

import {sendMessage} from "util/ws"

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
            sendMessage({id: this.id, text: this.text});
            this.text = '';
            this.id = '';
        }
    }
}
</script>

<style>

</style>