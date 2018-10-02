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
        messageAttr(newVal, oldVal)   {
            this.id = newVal.id;
            this.text = newVal.text;
        }
    },
    methods: {
        save() {
            sendMessage({id: this.id, text: this.text});
            this.text = '';
            this.id = '';

            /*const message = {text: this.text};
            if (this.id) {
                this.$resource('/message{/id}').update({ id: this.id }, message)
                    .then(result => result.json())
                    .then(data => {
                        const index = getIndex(this.messages, this.id);
                        this.messages.splice(index, 1, data);
                        this.text = '';
                        this.id = '';
                    });
            } else {
                this.$resource('/message{/id}').save({}, message)
                    .then(result => result.json())
                    .then(data => {
                        this.messages.push(data);
                        this.text = '';
                    });
            }*/
        }
    }
}
</script>

<style>

</style>