<template>

    <b-input-group class="my-3">
        <b-form-input placeholder="Write something" v-model="text" @keyup.enter.native="save" />
        <b-input-group-append>
            <b-btn variant="outline-primary" @click="save">Save</b-btn>
        </b-input-group-append>
    </b-input-group>

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